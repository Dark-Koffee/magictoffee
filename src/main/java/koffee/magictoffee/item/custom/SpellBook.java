package koffee.magictoffee.item.custom;

import koffee.magictoffee.spells.Spell;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;

public class SpellBook extends Item {
    public SpellBook(Settings settings) {
        super(settings);
    }

    // Method to set the spell
    public static void setSpell(ItemStack stack, Spell spell) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("Spell", spell.getID());
    }

    // Method to get the spell
    public static String getSpell(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getString("Spell"): null;
    }
}
