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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class MagicMissile extends Spell {

    public MagicMissile() {
        super.spellID = "magictoffee:magicmissile";
        super.displayName = "Magic Missile";
    }

    @Override
    public void ActionOnUse(PlayerEntity player){
        // In order to draw particles later
        ServerWorld world = (ServerWorld) player.getWorld();
        Vec3d eyePos = player.getEyePos();

        // Get the player's target entity
        TargetEntityResult targetEntityResult = getTargetEntity(player, 20.0D);
        // Sets things to null in case there's not an entity
        Entity targetEntity = null;
        Vec3d targetEntityLoc = null;
        if (targetEntityResult != null) {
            targetEntity = targetEntityResult.getEntity();
            targetEntityLoc = targetEntityResult.getHitPos();
        }
        BlockPos targetBlock = getTargetBlock(player, 20.0D, false);
        Vec3d targetBlockLoc = null;
        if (targetBlock != null) {
            targetBlockLoc = new Vec3d(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
        }


        // Now, some people may say
        // "Wait a second toffee, that looks like a lot of reused code why don't you just use funct-" Shhhhhhh
        // To those people I say, If it ain't broke, don't fix it.


        // If it doesn't hit anything
        if (targetEntity == null && targetBlock == null) {
            world.spawnParticles(ParticleTypes.ELECTRIC_SPARK, eyePos.x, eyePos.y, eyePos.z, 30, 0.1, 0.1, 0.1, 0);
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
        } else {
            // If only the block is valid
            if (targetEntity == null) {
                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);
                Particles.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            }
            // If only the entity is valid
            else if (targetBlock == null) {
                // If it's a living entity
                if (targetEntity instanceof LivingEntity) {
                    targetEntityLoc = targetEntity.getEyePos();
                    // Move line down 0.2 blocks
                    targetEntityLoc = new Vec3d(targetEntityLoc.x, targetEntityLoc.y - 0.2, targetEntityLoc.z);
//                ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth()-1);
                    targetEntity.damage(targetEntity.getDamageSources().generic(), 2.0F);
                }

                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);

                // Draw particle line
                Particles.drawLine(world, eyePos, targetEntityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            // Neither is null
            // If the target entity is closer
            else if (targetEntityLoc.distanceTo(eyePos) < targetBlockLoc.distanceTo(eyePos)) {
                // If it's a living entity
                if (targetEntity instanceof LivingEntity) {
                    targetEntityLoc = targetEntity.getEyePos();
                    // Move line down 0.2 blocks
                    targetEntityLoc = new Vec3d(targetEntityLoc.x, targetEntityLoc.y - 0.2, targetEntityLoc.z);
//                ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth()-1);
                    targetEntity.damage(targetEntity.getDamageSources().generic(), 2.0F);
                }

                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);

                // Draw particle line
                Particles.drawLine(world, eyePos, targetEntityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            // If the target block is closer
            else {
                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);
                Particles.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            }
        }
    }


    public BlockPos getTargetBlock(PlayerEntity player, double range, boolean includeFluids) {
        // Perform raycast from current position to endpoint
        HitResult hitResult = player.raycast(range, 1.0F, includeFluids);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            return blockHitResult.getBlockPos();
            //return blockPos; // Return the block position
        // If it's not a block
        } else {
            return null;
        }
//            HAH as if it's going to hit an entity. That'd be way too convenient
//        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
//            //EntityHitResult entityHitResult = (EntityHitResult) hitResult;
//            currentPos = hitResult.getPos(); // Continue from entity hit position
//        }
    }


    public static TargetEntityResult getTargetEntity(PlayerEntity player, double range) {
        // Look, I'm aware that this is possibly the stupidest way to do this. However. It's the only way I knew how
        // Hours were spent looking for a better way, we're flippin' using this.
        ServerWorld world = ((ServerWorld) player.getWorld());
        Vec3d playerPos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);

        // Calculate step vector in the direction of the player's look vector
        Vec3d stepVec = lookVec.multiply(0.1);

        // Iterate through points along the line of sight up to MAX_DISTANCE
        for (double distance = 0.0D; distance <= range; distance += 0.1) {
            Vec3d point = playerPos.add(lookVec.multiply(distance));
            double leeway = 0.05;
            Box searchBox = new Box(point.getX() - leeway, point.getY() - leeway, point.getZ() - leeway,
                    point.getX() + leeway, point.getY() + leeway, point.getZ() + leeway);

            // Check for entities within the search box
            for (Entity entity : world.getOtherEntities(player, searchBox)) {
                if (entity != player && entity.getBoundingBox().intersects(searchBox)) {
                    return new TargetEntityResult(entity, point);
                }
            }
        }

        return null; // No entity found
    }

    // Custom class to hold the result of getTargetEntity
    public static class TargetEntityResult {
        private final Entity entity;
        private final Vec3d hitPos;

        public TargetEntityResult(Entity entity, Vec3d hitPos) {
            this.entity = entity;
            this.hitPos = hitPos;
        }

        public Entity getEntity() {
            return entity;
        }

        public Vec3d getHitPos() {
            return hitPos;
        }
    }
}
