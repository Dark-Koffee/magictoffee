package koffee.magictoffee.spells;

import koffee.magictoffee.util.Particles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class HealSpell extends Spell{

    public HealSpell() {
        super.spellID = "magictoffee:heal";
        super.displayName = "Heal";
        super.manaCost = 20;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player) {

        // Play fwip sound for the player
        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), SoundCategory.AMBIENT, 1.0F, 2.0F);

        // Draw 3 rings of hearts around the player
        Vec3d loc = player.getPos();
        for (double i = 0; i < 1.5; i += 0.5) {
            Particles.drawCircle((ServerWorld) player.getWorld(), new Vec3d(loc.getX(), loc.getY()+i, loc.getZ()), 1F, 0F, 0F, 24, ParticleTypes.HEART, 1);
        }
        // Heal and feed the player
        player.heal(4);
        player.getHungerManager().add(4, 0.5F);

        return true;
    }
}