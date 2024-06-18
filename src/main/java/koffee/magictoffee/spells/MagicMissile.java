package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class MagicMissile extends Spell {

    MagicMissile() {
        super.spellID = "magictoffee:magicmissile";
        super.displayName = "Magic Missile";
    }

    @Override
    public void ActionOnUse(PlayerEntity user){
        user.sendMessage(Text.literal("-- Spells --"), false);
        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 2.0F, 1.0F);
        for(int i = 0; i < SpellRegisterer.spells.size(); i++) {
            user.sendMessage(Text.of(SpellRegisterer.spells.get(i).getID()), false);
        }
    }

}
