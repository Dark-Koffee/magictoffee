package koffee.magictoffee.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class SpellData {
    public static int addSelected(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        // Selected is set to 0 if it doesn't exist in the player's nbt
        int selected = 0;
        if (nbt.contains("selected", NbtElement.INT_TYPE)) {
            selected = nbt.getInt("selected");
        }

        selected += amount;
        if (selected > 4) {
            selected = 0;
        } else if (selected < 0) {
            selected = 4;
        }
        nbt.putInt("selected", selected);
        return selected;
    }
    public static String addSpell(IEntityDataSaver player, int spellIndex, String spellName) {
        NbtCompound nbt = player.getPersistentData();

        // Define the keys for spell slots
        String[] spellKeys = {"spell0", "spell1", "spell2", "spell3", "spell4"};

        // Validate spellIndex to be within bounds
        if (spellIndex < 0 || spellIndex >= spellKeys.length) {
            throw new IllegalArgumentException("Spell index out of bounds");
        }

        // Get the current value of the spell slot from NBT, defaulting to null if it doesn't exist
        String currentSpell = nbt.getString(spellKeys[spellIndex]);

        // Set default value to an empty string if the current value is null (not set)
        if (currentSpell.isEmpty()) {
            currentSpell = spellName; // Set to the new spell name
        }

        // Update the spell slot in NBT with the new spell name
        nbt.putString(spellKeys[spellIndex], spellName);

        // Return the updated spell name
        return spellName;
    }
}
