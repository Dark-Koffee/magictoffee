package koffee.magictoffee.spells;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class TestSpell extends Spell {

    TestSpell() {
        super.spellID = "magictoffee:test";
        super.displayName = "Test Spell";
        super.description = "The first spell we made in order to test spells and give us information";
        super.bookName = this.displayName;
        super.cooldown = 40;
        super.manaCost = 0;
        super.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.spellID);
//        super.registerBook = false;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        player.sendMessage(Text.literal("-- Spells --"), false);
        player.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 2.0F, 1.0F);
        for(int i = 0; i < SpellRegisterer.spells.size(); i++) {
            player.sendMessage(Text.of(SpellRegisterer.spells.get(i).getID()), false);
        }
        return true;
    }

}
