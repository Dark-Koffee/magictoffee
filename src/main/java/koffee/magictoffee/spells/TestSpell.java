package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;

public class TestSpell extends Spell {

    @Override
    public void ActionOnUse(PlayerEntity user){
        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
    }

}
