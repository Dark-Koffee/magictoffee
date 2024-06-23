package koffee.magictoffee.particles;

import koffee.magictoffee.MagicToffee;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ParticleFactory implements net.minecraft.client.particle.ParticleFactory<DefaultParticleType> {
    private final SpriteProvider spriteProvider;

    public ParticleFactory(SpriteProvider spriteProvider) {
        this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        HeartParticle particle = new HeartParticle(world, x, y, z, velocityX, velocityY, velocityZ);
        particle.setSprite(this.spriteProvider);
        return particle;
    }
    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(MagicToffee.HEART, ParticleFactory::new);
    }
}