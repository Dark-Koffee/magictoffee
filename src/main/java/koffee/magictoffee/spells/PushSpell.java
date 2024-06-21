package koffee.magictoffee.spells;

import koffee.magictoffee.util.Particles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class PushSpell extends Spell{

    public PushSpell() {
        super.spellID = "magictoffee:push";
        super.displayName = "Push";
    }

    @Override
    public void ActionOnUse(PlayerEntity player) {
        Vec3d targetBlock = getTargetBlock(player);
        Vec3d playerPos = player.getEyePos();

        if (targetBlock != null) {
            // Get vector between player and block
            Vec3d vector = playerPos.subtract(targetBlock);

            // Get vector to push player with
            Vec3d desiredVector = (vector.normalize()).multiply(1.5D);

            // Get location to draw particles at
            Vec3d offset = vector.normalize().multiply(0.75);
            Vec3d particlePos = targetBlock.add(offset);

            // Using addVelocity for additive effect
            player.addVelocity(desiredVector);

            // Play whoosh sound for the player
            player.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.AMBIENT, 1.0F, 2.0F);

            // Draw the circle
            Particles.drawCircle((ServerWorld) player.getWorld(), particlePos, 0.75D, player.headYaw-90, player.getPitch()-90, 16, ParticleTypes.INSTANT_EFFECT, 0.0);

            // Update on the client
            player.velocityModified = true;
        } else {
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
        }
    }

    private Vec3d getTargetBlock(PlayerEntity player) {
        HitResult hitResult = player.raycast(10.0D, 0.0F, true);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult targetBlock = (BlockHitResult) hitResult;
            return targetBlock.getPos();
        }
        else {
            return null;
        }
    }
}
