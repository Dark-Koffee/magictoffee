package koffee.magictoffee.util;
import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.spells.ModSpells;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.SpellRegisterer;
import net.minecraft.entity.player.PlayerEntity;

public class SpellData {
    public static int addSelected(PlayerEntity player, int amount) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        int selected = component.getSelected();
        component.setSelected(selected+amount);
        return selected+amount;
    }
    public static int getSelected(PlayerEntity player) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        // Selected is set to 0 if it doesn't exist
        int selected = 0;
        selected = component.getSelected();
        return selected;
    }
    public static void setSpell(PlayerEntity player, int spellIndex, String spellName) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);

        // Validate spellIndex to be within bounds
        if (spellIndex < 0 || spellIndex >= 5) {
            throw new IllegalArgumentException("Spell index out of bounds");
        }

        // Update the spell slot in NBT with the new spell name
        component.setSpell(spellIndex, spellName);
    }

    public static String getSpell(PlayerEntity player, int spellIndex) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);

        // Validate spellIndex to be within bounds
        if (spellIndex < 0 || spellIndex >= 5) {
            throw new IllegalArgumentException("Spell index out of bounds");
        }

        // Get the current value of the spell slot from NBT, defaulting to null if it doesn't exist
        String currentSpell = component.getSpell(spellIndex);

        // Set default value to an empty spell if the current value is null
        if (currentSpell == null) {
            currentSpell = "magictoffee:empty";
        }

        // Return the requested spell
        return currentSpell;
    }

    public static String getSpellName(String spellID) {
        for (int i = 0; i < SpellRegisterer.spells.size(); i++) {
            if (SpellRegisterer.spells.get(i).getID().equals(spellID)) {
                return SpellRegisterer.spells.get(i).getDisplayName();
            }
        }
        return "Invalid Spell";
    }

    public static Spell getSpellFromID(String spellID) {
        for (int i = 0; i < SpellRegisterer.spells.size(); i++) {
            if (SpellRegisterer.spells.get(i).getID().equals(spellID)) {
                return SpellRegisterer.spells.get(i);
            }
        }
        return ModSpells.emptySpell;
    }
}