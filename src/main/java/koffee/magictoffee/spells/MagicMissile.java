package koffee.magictoffee.spells;

import koffee.magictoffee.util.Particles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class MagicMissile extends Spell {

    public MagicMissile() {
        super.spellID = "magictoffee:magicmissile";
        super.displayName = "Magic Missile";
    }

    @Override
    public void ActionOnUse(PlayerEntity player){
        Vec3d eyePos = player.getEyePos();
        String targetType = getTargetType(player, 20.0D, true);
        ServerWorld world = (ServerWorld) player.getWorld();
        if (targetType == null) {
            player.sendMessage(Text.literal("Air!"), false);
            world.spawnParticles(ParticleTypes.ELECTRIC_SPARK, eyePos.x, eyePos.y, eyePos.z, 30, 0.1, 0.1, 0.1, 0);
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
        } else if (targetType.equals("Entity")) {
            player.sendMessage(Text.literal("Entity!"), false);
            Entity entity = getTargetEntity(player, 20.0D, true);
            Vec3d entityLoc = entity.getPos();
            if (entity instanceof LivingEntity) {
                entityLoc = entity.getEyePos();
                // Move line down 0.2 blocks
                entityLoc = new Vec3d(entityLoc.x, entityLoc.y-0.2, entityLoc.z);
//                ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth()-1);
                entity.damage(entity.getDamageSources().generic(), 2.0F);
            }
            // Move line down 0.2 blocks
            eyePos = new Vec3d(eyePos.x, eyePos.y-0.2, eyePos.z);
            Particles.drawLine(world, eyePos, entityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
            player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        else if (targetType.equals("Block")) {
            player.sendMessage(Text.literal("Block!"), false);
            // Move line down 0.2 blocks
            eyePos = new Vec3d(eyePos.x, eyePos.y-0.2, eyePos.z);
            Particles.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
        }
    }

    // Gets the position of the entity the player is looking at
    private String getTargetType(PlayerEntity player, double range, boolean includeFluids) {
        // Gets the player's eye position
        //Vec3d playerEyePos = player.getEyePos();
        // Gets the player's view direction
        //Vec3d playerLookVec = player.getRotationVec(1.0F);
        // Gets the first hit result block/entity
        HitResult hitResult = player.raycast(range, 1.0F, includeFluids);

        if (hitResult.getType() == HitResult.Type.ENTITY) {
            return "Entity";
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            return "Block";
        } else {
            return null;
        }
    }

    // Only call this if we KNOW it's an entity
    private Entity getTargetEntity(PlayerEntity player, double range, boolean includeFluids) {
        EntityHitResult hitResult = (EntityHitResult) player.raycast(range, 1.0F, includeFluids);
        return hitResult.getEntity();
    }

    private BlockPos getTargetBlock(PlayerEntity player, double range, boolean includeFluids) {
        Vec3d start = player.getCameraPosVec(1.0F); // Starting point: player's camera position
        Vec3d direction = player.getRotationVec(1.0F); // Direction: player's rotation vector

        Vec3d currentPos = start;
        int maxIterations = (int) Math.ceil(range); // Maximum number of iterations (blocks to check)

        for (int i = 0; i < maxIterations; i++) {
            Vec3d endpoint = currentPos.add(direction.multiply(range));

            // Perform raycast from current position to endpoint
            HitResult hitResult = player.getWorld().raycast(new RaycastContext(
                    currentPos, endpoint, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();

                // Check if block hit is within maxDistance
                double distanceToBlock = currentPos.distanceTo(Vec3d.of(blockPos));
                if (distanceToBlock <= range) {
                    return blockPos; // Return the block position
                } else {
                    break; // Break the loop if block is beyond maxDistance
                }
            } else if (hitResult.getType() == HitResult.Type.MISS) {
                break; // No block hit, break the loop
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                //EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                currentPos = hitResult.getPos(); // Continue from entity hit position
            }
        }

        return null; // Return null if no valid block position found within maxDistance
    }

}
