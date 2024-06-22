package koffee.magictoffee.spells;

import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.util.KoffeeSpellTools;
import koffee.magictoffee.util.Particles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class PullSpell extends Spell{

    public PullSpell() {
        super.spellID = "magictoffee:pull";
        super.displayName = "Pull";
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player) {
        Vec3d targetBlock = KoffeeSpellTools.getTargetBlock(player, 30, false);
        Vec3d playerPos = player.getPos();

        if (targetBlock != null) {
            // Get vector between player and block
            Vec3d vector = targetBlock.subtract(playerPos);

            // Get vector to pull player with
            Vec3d desiredVector = (vector.normalize()).multiply(2D);

            // Using addVelocity for additive effect
            player.addVelocity(desiredVector);

            // Play fwip sound for the player
            player.playSound(SoundEvents.ENTITY_GOAT_LONG_JUMP, SoundCategory.AMBIENT, 1.0F, 2.0F);

            // Draw a line between the block and the player
            Particles.drawLine((ServerWorld) player.getWorld(), player.getEyePos(), targetBlock, 50, ParticleTypes.ELECTRIC_SPARK, 0);

            // Update on the client
            player.velocityModified = true;
            return true;
        } else {
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            ModComponents.SPELLS_COMPONENT_KEY.get(player).setCooldown(spellID, 0);
            return false;
        }
    }
}
