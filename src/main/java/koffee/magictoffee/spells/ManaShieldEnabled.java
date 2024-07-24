package koffee.magictoffee.spells;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.util.SpellData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ManaShieldEnabled extends Spell {

    ManaShieldEnabled() {
        super.spellID = "magictoffee:mana_shield_enabled";
        super.displayName = "Mana Shield (ON)";
        super.description = "When you take damage, your mana will decrease instead of your health";
        super.bookName = "Mana Shield";
        super.cooldown = 0;
        super.manaCost = 0;
        super.spellBook = ModSpells.manaShield.getSpellBook();
        super.registerBook = false;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        for (int i = 0; i < 5; i++) {
            if (SpellData.getSpell(player, i).equals(getID())) {
                SpellData.setSpell(player, i, ModSpells.manaShield.getID());
            }
        }
        return true;
    }

}
