package koffee.magictoffee.spells;

import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.util.KoffeeSpellTools;
import koffee.magictoffee.util.Particles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

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


        // If it doesn't hit anything
        if (targetEntity == null && targetBlock == null) {
            Particles.drawCircle(world, eyePos, 0.5, 0, 0, 32, ParticleTypes.INSTANT_EFFECT, 0);
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            ModComponents.SPELLS_COMPONENT_KEY.get(player).setCooldown(spellID, 0);
            return false;
        } else {
            // If only the block is valid
            if (targetEntity == null) {
                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);
                Particles.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
                ModComponents.SPELLS_COMPONENT_KEY.get(player).setCooldown(spellID, player.getWorld().getTime()-5L);
                return false;
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

                // pushes the target entity backwards
                Vec3d velocity = (targetEntity.getPos()).subtract(player.getPos());
                Vec3d desiredVelocity = (velocity.normalize()).multiply(0.5);
                targetEntity.addVelocity(desiredVelocity.x, 0.5, desiredVelocity.z);

                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);

                // Draw particle line
                Particles.drawLine(world, eyePos, targetEntityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
            // Neither is null
            // If the target entity is closer
            else if (targetEntityLoc.distanceTo(eyePos) < targetBlock.distanceTo(eyePos)) {
                // If it's a living entity
                if (targetEntity instanceof LivingEntity) {
                    targetEntityLoc = targetEntity.getEyePos();
                    // Move line down 0.2 blocks
                    targetEntityLoc = new Vec3d(targetEntityLoc.x, targetEntityLoc.y - 0.2, targetEntityLoc.z);
//                ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth()-1);
                    targetEntity.damage(targetEntity.getDamageSources().generic(), 2.0F);
                }

                // pushes the target entity backwards
                Vec3d velocity = (targetEntity.getPos()).subtract(player.getPos());
                Vec3d desiredVelocity = (velocity.normalize()).multiply(0.5);
                targetEntity.addVelocity(desiredVelocity.x, 0.5, desiredVelocity.z);

                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);

                // Draw particle line
                Particles.drawLine(world, eyePos, targetEntityLoc, 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
            // If the target block is closer
            else {
                // Move line down 0.2 blocks
                eyePos = new Vec3d(eyePos.x, eyePos.y - 0.2, eyePos.z);
                Particles.drawLine(world, eyePos, player.raycast(20.0D, 1.0F, true).getPos(), 30, ParticleTypes.ELECTRIC_SPARK, 0);
                player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
                ModComponents.SPELLS_COMPONENT_KEY.get(player).setCooldown(spellID, player.getWorld().getTime()-5L);
                return false;
            }
        }
    }
}
