package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
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
            Vec3d desiredVector = (vector.normalize()).multiply(2);
            player.setVelocityClient(desiredVector.x, desiredVector.y, desiredVector.z);
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
