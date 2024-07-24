package koffee.magictoffee.spells;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.util.SpellData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class ManaShield extends Spell {

    ManaShield() {
        super.spellID = "magictoffee:mana_shield";
        super.displayName = "Mana Shield (OFF)";
        super.description = "When you take damage, your mana will decrease instead of your health";
        super.bookName = "Mana Shield";
        super.cooldown = 0;
        super.manaCost = 0;
        super.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.spellID);
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        for (int i = 0; i < 5; i++) {
            if (SpellData.getSpell(player, i).equals(getID())) {
                SpellData.setSpell(player, i, ModSpells.manaShieldEnable.getID());
            }
        }
        return true;
    }

}
