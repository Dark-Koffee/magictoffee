package koffee.magictoffee.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class DrawTools {

    // Draws a line of particles between the two points
    public static void drawLine(ServerWorld world, Vec3d start, Vec3d end, int lineDensity, ParticleEffect particle, double particleSpeed) {
        // gets the direction of the line and the distance
        Vec3d direction = end.subtract(start);
        double distance = direction.length();

        Vec3d normalizedDirection = direction.normalize(); // Normalises the distance
        double interval = distance / lineDensity; // makes sure the particles are distributed evenly

        // Places particles on the interpolated points
        for (int i = 0; i <= lineDensity; i++) {
            double t = i * interval;
            Vec3d point = start.add(normalizedDirection.multiply(t));
            world.spawnParticles(particle, point.x, point.y, point.z, 1, 0, 0, 0, particleSpeed);
        }
    }

    // Draws a circle of particles around a point
    public static void drawCircle(ServerWorld world, Vec3d center, double radius, double yaw, double pitch, int step, ParticleEffect particle, double particleSpeed) {
        if (!world.isClient) {
            // Reference vector from yaw + 90 and pitch 0
            Vec3d reference = new Vec3d(Math.cos(Math.toRadians(yaw + 90)), 0, Math.sin(Math.toRadians(yaw + 90)));

            // Spherical vector with given radius
            Vec3d v = new Vec3d(radius, 0, 0);

            // Calculate the number of steps
            int steps = (int) Math.floor((double) 360 / step);

            for (int i = 0; i < steps; i++) {
                // Rotate vector v around Y-axis by step size
                Vec3d tempV = new Vec3d(v.x, v.y, v.z).rotateY((float) Math.toRadians(i * step));

                // If pitch is not zero, rotate around the reference vector
                if (pitch != 0) {
                    tempV = rotateAroundAxis(tempV, reference, Math.toRadians(pitch));
                }

                // Calculate the point in world space
                Vec3d point = center.add(tempV);

                // Spawn particles at the calculated point
                world.spawnParticles(
                        particle,
                        point.x,
                        point.y,
                        point.z,
                        10,      // Number of particles
                        0.0,     // X offset
                        0.0,     // Y offset
                        0.0,     // Z offset
                        particleSpeed      // Speed of particles
                );
            }
        }
    }

    private static Vec3d rotateAroundAxis(Vec3d vec, Vec3d axis, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double dot = vec.dotProduct(axis);
        double x = (cos + (1 - cos) * axis.x * axis.x) * vec.x +
                ((1 - cos) * axis.x * axis.y - axis.z * sin) * vec.y +
                ((1 - cos) * axis.x * axis.z + axis.y * sin) * vec.z;
        double y = ((1 - cos) * axis.y * axis.x + axis.z * sin) * vec.x +
                (cos + (1 - cos) * axis.y * axis.y) * vec.y +
                ((1 - cos) * axis.y * axis.z - axis.x * sin) * vec.z;
        double z = ((1 - cos) * axis.z * axis.x - axis.y * sin) * vec.x +
                ((1 - cos) * axis.z * axis.y + axis.x * sin) * vec.y +
                (cos + (1 - cos) * axis.z * axis.z) * vec.z;
        return new Vec3d(x, y, z);
    }
}
