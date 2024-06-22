package koffee.magictoffee.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class KoffeeSpellTools {



    public static Vec3d getTargetBlock(PlayerEntity player, double range, boolean includeFluids) {
        // Perform raycast from current position to endpoint
        HitResult hitResult = player.raycast(range, 1.0F, includeFluids);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            return blockHitResult.getPos();
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
        double stepSize = 0.1;
        double leeway = stepSize / 2.0D;

        // Iterate through points along the line of sight up to MAX_DISTANCE
        for (double distance = 0.0D; distance <= range; distance += stepSize) {
            Vec3d point = playerPos.add(lookVec.multiply(distance));
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
