package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
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
            Vec3d vector = playerPos.subtract(targetBlock);
            Vec3d desiredVector = (vector.normalize()).multiply(1.5D);

            // Using addVelocity for additive effect
            player.addVelocity(desiredVector);
            player.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1.0F, 2.0F);

            // Update on the client
            player.velocityModified = true;
        } else {
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, 1.0F, 1.0F);
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
