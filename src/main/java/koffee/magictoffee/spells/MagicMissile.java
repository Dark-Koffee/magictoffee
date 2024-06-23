package koffee.magictoffee.spells;

import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.damage.ModDamageTypes;
import koffee.magictoffee.util.KoffeeSpellTools;
import koffee.magictoffee.util.DrawTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class MagicMissile extends Spell {

    public MagicMissile() {
        super.spellID = "magictoffee:magicmissile";
        super.displayName = "Magic Missile";
        super.cooldown = 10;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        // In order to draw particles later
        ServerWorld world = (ServerWorld) player.getWorld();
        Vec3d eyePos = player.getEyePos();

        // Get the player's target entity
        KoffeeSpellTools.TargetEntityResult targetEntityResult = KoffeeSpellTools.getTargetEntity(player, 20.0D);
        // Sets things to null in case there's not an entity
        Entity targetEntity = null;
        Vec3d targetEntityLoc = null;
        if (targetEntityResult != null) {
            targetEntity = targetEntityResult.getEntity();
            targetEntityLoc = targetEntityResult.getHitPos();
        }
        Vec3d targetBlock = KoffeeSpellTools.getTargetBlock(player, 20.0D, false);


        // Now, some people may say
        // "Wait a second toffee, that looks like a lot of reused code why don't you just use funct-" Shhhhhhh
        // To those people I say, If it ain't broke, don't fix it. ~~ Toffee 2024

        // Fine I caved in and function-ed it. BUT IT WAS BEGRUDGINGLY

        // If it doesn't hit anything
        if (targetEntity == null && targetBlock == null) {
            DrawTools.drawCircle(world, eyePos, 0.5, 0, 0, 32, ParticleTypes.INSTANT_EFFECT, 0);
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
            magicComponent.setCooldown(spellID, player.getWorld().getTime() - cooldown/2);
            magicComponent.setMana(magicComponent.getMana()+manaCost);
            return false;
        // If it hit something
        } else {
            // If only the block is valid - hit block
            if (targetEntity == null) {
                return hitBlockAction(eyePos, player, world);
            }
            // If only the entity is valid - hit entity
            else if (targetBlock == null) {
                return hitEntityAction(targetEntity, targetEntityLoc, player, eyePos, world);
            }
            // Neither is null
            // If the target entity is closer - hit entity
            else if (targetEntityLoc.distanceTo(eyePos) < targetBlock.distanceTo(eyePos)) {
                return hitEntityAction(targetEntity, targetEntityLoc, player, eyePos, world);
            }
            // If the target block is closer - hit block
            else {
                return hitBlockAction(eyePos, player, world);
            }
        }
    }

    private boolean hitEntityAction(Entity targetEntity, Vec3d targetEntityLoc, PlayerEntity player, Vec3d eyePos, ServerWorld world) {
        // If it's a living entity
        if (targetEntity instanceof LivingEntity) {
            targetEntityLoc = targetEntity.getEyePos();
            // Move line down 0.2 blocks
            targetEntityLoc = new Vec3d(targetEntityLoc.x, targetEntityLoc.y - 0.2, targetEntityLoc.z);
            //targetEntity.damage(targetEntity.getDamageSources().generic(), 2.0F);
            targetEntity.damage(ModDamageTypes.of(world, ModDamageTypes.MAGICMISSILE, player, targetEntity), 4.0F);
            ((LivingEntity) targetEntity).setAttacker(player);
            if (targetEntity instanceof Angerable) {
                ((Angerable) targetEntity).setAngryAt(player.getUuid());
            }
            if (targetEntity instanceof AnimalEntity) {
                fleeFromPlayer((AnimalEntity) targetEntity, player);
            }
            if (targetEntity instanceof VillagerEntity) {
                List<IronGolemEntity> ironGolems = world.getEntitiesByClass(IronGolemEntity.class,
                        targetEntity.getBoundingBox().expand(16.0),
                        golem -> !golem.isDead());

                // Set the player as the target for each Iron Golem found
                for (IronGolemEntity golem : ironGolems) {
                    golem.setTarget(player);
                }
            }


//            targetEntity.damage(new DamageSource(targetEntity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageTypes.MAGICMISSILE)), 1.0F);
        }
            // pushes the target entity backwards
            Vec3d velocity = (targetEntity.getPos()).subtract(player.getPos());
            Vec3d desiredVelocity = (velocity.normalize()).multiply(0.5);
            targetEntity.setVelocity(desiredVelocity.x, 0.5, desiredVelocity.z);

        // Move line down 0.2 blocks
        eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);

        // Draw particle line
        DrawTools.drawLine(world, eyePos, targetEntityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
        player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return true;
    }
    private boolean hitBlockAction(Vec3d eyePos, PlayerEntity player, ServerWorld world) {
        // Move line down 0.2 blocks
        eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);
        DrawTools.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
        player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
        MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        magicComponent.setCooldown(spellID, player.getWorld().getTime() - cooldown/2);
        magicComponent.setMana(magicComponent.getMana()+manaCost);
        return false;
    }
    private void fleeFromPlayer(AnimalEntity animal, PlayerEntity player) {
        // Calculate direction away from player's position
        Vec3d playerPos = player.getPos();
        Vec3d entityPos = animal.getPos();
        Vec3d fleeDirection = entityPos.subtract(playerPos).normalize();

        // Adjust movement to flee in the opposite direction
        double fleeDistance = 8.0; // Adjust as needed
        double fleeX = animal.getX() + fleeDirection.x * fleeDistance;
        double fleeZ = animal.getZ() + fleeDirection.z * fleeDistance;

        // Set the destination to flee from the player
        animal.getNavigation().startMovingTo(fleeX, animal.getY(), fleeZ, 1.0);
    }
}
