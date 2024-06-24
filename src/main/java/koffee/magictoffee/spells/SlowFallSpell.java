package koffee.magictoffee.spells;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class SlowFallSpell extends Spell {

    SlowFallSpell() {
        super.spellID = "magictoffee:slow_fall";
        super.displayName = "Slow Fall";
        super.bookName = this.displayName;
        super.cooldown = 40;
        super.manaCost = 10;
        super.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.spellID);
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        ServerWorld world = (ServerWorld) player.getWorld();
        // Spawn cloud particles
        world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 30, 0, 0, 0, 0.1);
        // Effect player w/ slow falling
        player.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0), player);
        // Play flap sound
        player.playSound(SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.AMBIENT, 1F, 1F);
        // Change player's velocity
        Vec3d playerVel = player.getVelocity();
        player.setVelocity(new Vec3d(playerVel.x, 0.1, playerVel.z));
        player.velocityModified = true;
        return true;
    }

}
