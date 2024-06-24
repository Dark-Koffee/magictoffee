package koffee.magictoffee.spells;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SpellRegisterer {
    public static List<Spell> spells = new ArrayList<>();

    public Spell register(Spell spell) {
        // Add spell to spell list
        spells.add(spell);
        // Make spell book
        if (spell.registerBook) {
            ItemStack stack = new ItemStack(ModItems.spellbook);
            SpellBook.setSpell(stack, spell.getID());
        }
        // Return
        return spell;
    }

    public static String[] spellList() {
        String[] spellNames = new String[spells.size()];
        for (int i = 0; i < spells.size(); i++) {
            spellNames[i] = spells.get(i).getID();
        }
        return spellNames;
    }
}

