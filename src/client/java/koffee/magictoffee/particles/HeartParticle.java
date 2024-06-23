package koffee.magictoffee.particles;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;

public class HeartParticle extends SpriteBillboardParticle {
    protected HeartParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale = 0.1f; // Set the scale of the particle
        this.maxAge = 20; // Set the lifetime of the particle
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY -= 0.04 * this.gravityStrength; // Simulate gravity
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.98;
            this.velocityY *= 0.98;
            this.velocityZ *= 0.98;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}