package koffee.magictoffee.util;
import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.spells.ModSpells;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.SpellRegisterer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpellData {
    public static int addSelected(PlayerEntity player, int amount) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        int selected = component.getSelected();
        selected = selected + amount;
        // Loop back around when out of range
        if (selected < 0) {
            selected = 4;
        } else if (selected > 4) {
            selected = 0;
        }
        // Set component selected and return selected
        component.setSelected(selected);
        return selected;
    }
    public static int setSelected(PlayerEntity player, int selected) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        if (0 <= selected && selected <= 4) {
            component.setSelected(selected);
            return selected;
        }
        return 0;
    }
    public static int getSelected(PlayerEntity player) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        // Selected is set to 0 if it doesn't exist
        return component.getSelected();
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
    public static int getMana(PlayerEntity player) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        return component.getMana();
    }

    public static int setMana(PlayerEntity player, int mana) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        component.setMana(mana);
        return mana;
    }

    public static int setManaRegen(PlayerEntity player, int manaRegen) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        component.setManaRegen(manaRegen);
        if (!player.getWorld().isClient()) { MagicToffee.refreshManaRegenTask(((ServerPlayerEntity) player)); }
        return manaRegen;
    }
    public static int getManaRegen(PlayerEntity player) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        return component.getManaRegen();
    }

    public static int setManaCap(PlayerEntity player, int manaCap) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        component.setManaCap(manaCap);
        return manaCap;
    }
    public static int getManaCap(PlayerEntity player) {
        MagicComponent component = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        return component.getManaCap();
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