package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class TestSpell extends Spell {

    TestSpell() {
        super.spellID = "magictoffee:test";
        super.displayName = "Test Spell";
    }

    @Override
    public void ActionOnUse(PlayerEntity player){
        player.sendMessage(Text.literal("-- Spells --"), false);
        player.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 2.0F, 1.0F);
        for(int i = 0; i < SpellRegisterer.spells.size(); i++) {
            player.sendMessage(Text.of(SpellRegisterer.spells.get(i).getID()), false);
        }
    }

}
