package koffee.magictoffee.spells;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class SlowFallSpell extends Spell {

    SlowFallSpell() {
        super.spellID = "magictoffee:slow_fall";
        super.displayName = "Slow Fall";
        super.cooldown = 40;
        super.manaCost = 10;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        ServerWorld world = (ServerWorld) player.getWorld();
        world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 30, 0, 0, 0, 0.1);
        player.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0), player);
        player.playSound(SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.AMBIENT, 1F, 1F);
        return true;
    }

}
