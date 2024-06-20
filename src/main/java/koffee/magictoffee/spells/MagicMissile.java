package koffee.magictoffee.spells;

import koffee.magictoffee.util.Particles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicMissile extends Spell {

    public MagicMissile() {
        super.spellID = "magictoffee:magicmissile";
        super.displayName = "Magic Missile";
    }

    @Override
    public void ActionOnUse(PlayerEntity player){
        Vec3d eyePos = player.getEyePos();
        Vec3d closestTarget = getClosest(player);
        Entity entity = getTargetEntity(player);
        World world = player.getWorld();
        if (entity != null) {
            Particles.drawLine(world, eyePos, entity.getEyePos(), 30, ParticleTypes.INSTANT_EFFECT);
            LivingEntity targetEntity = (LivingEntity) getTargetEntity(player);
            player.sendMessage(Text.of(targetEntity.toString()));
            targetEntity.addVelocity(0, 5, 0);

        }
        else {
            Particles.drawCircle(world, eyePos, 0.75, 0, 0, 24, ParticleTypes.INSTANT_EFFECT);
        }
    }

    // Gets the position of the entity the player is looking at
    private Entity getTargetEntity(PlayerEntity player) {
        // Gets the player's eye position
        Vec3d playerEyePos = player.getEyePos();
        // Gets the player's view direction
        Vec3d playerLookVec = player.getRotationVec(1.0F);

        double raycastRange = 20.0D;
        HitResult hitResult = player.raycast(raycastRange, 1.0F, false);

        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            return entityHitResult.getEntity();
        }

        return null;
    }

    private Vec3d getTargetBlock(PlayerEntity player) {
        HitResult hitResult = player.raycast(20.0D, 0.0F, false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult targetBlock = (BlockHitResult) hitResult;
            return targetBlock.getPos();
        }
        else {
            return null;
        }
    }

    // Gets the position of the closest target
    private Vec3d getClosest(PlayerEntity player) {
        Entity entity = getTargetEntity(player);
        Vec3d blockPos = getTargetBlock(player);
        Vec3d eyePos = player.getEyePos();

        if (entity != null && blockPos != null) {
            if (eyePos.distanceTo(entity.getPos()) > eyePos.distanceTo(blockPos)) {
                return entity.getPos();
            } else {
                return blockPos;
            }
        }
        else if (entity != null) {
            player.sendMessage(Text.of("returned entity position"));
            return entity.getPos();
        }
        else {
            return blockPos;
        }
    }

}
