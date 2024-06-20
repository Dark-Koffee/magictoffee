package koffee.magictoffee.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Particles {

    // Draws a line of particles between the two points
    public static void drawLine(World world, Vec3d start, Vec3d end, int lineDensity, ParticleEffect particle) {
        // gets the direction of the line and the distance
        Vec3d direction = end.subtract(start);
        double distance = direction.length();

        Vec3d normalizedDirection = direction.normalize(); // Normalises the distance
        double interval = distance / lineDensity; // makes sure the particles are distributed evenly

        // Places particles on the interpolated points
        for (int i = 0; i <= lineDensity; i++) {
            double t = i * interval;
            Vec3d point = start.add(normalizedDirection.multiply(t));
            world.addParticle(particle, point.x, point.y, point.z, 0, 0, 0);
        }
    }

    // Draws a circle of particles around a point
    public static void drawCircle(World world, Vec3d center, double radius, double yaw, double pitch, int step, ParticleEffect particle) {
        Vec3d reference = new Vec3d(Math.cos(Math.toRadians(yaw + 90)), 0, Math.sin(Math.toRadians(yaw + 90)));
        Vec3d v = new Vec3d(radius, 0, 0);

        int steps = (int) Math.floor((double) 360 / step);

        for (int i = 0; i < steps; i++) {
            Vec3d tempV = v.rotateY((float) Math.toRadians(i * step));

            if (pitch != 0) {
                tempV = rotateAroundAxis(tempV, reference, Math.toRadians(pitch));
            }

            Vec3d point = center.add(tempV);

            world.addParticle(particle, point.x, point.y, point.z, 0, 0, 0);
        }
    }

    private static Vec3d rotateAroundAxis(Vec3d vec, Vec3d axis, double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        return vec.multiply(cosTheta)
                .add(axis.crossProduct(vec).multiply(sinTheta))
                .add(axis.multiply(axis.dotProduct(vec) * (1 - cosTheta)));
    }
}
