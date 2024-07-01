package koffee.magictoffee.item.custom;

import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.util.KoffeeStringTools;
import koffee.magictoffee.util.SpellData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellBook extends Item {
    public SpellBook(Settings settings) {
        super(settings);
    }

    // Method to set the spell
    public static ItemStack setSpell(ItemStack stack, String spellID) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("Spell", spellID);
        return stack;
    }

    // Method to get the spell
    public static String getSpell(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getString("Spell"): null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String spellID = getSpell(stack);
        // Default spell
        Spell spell = new Spell() {
            @Override
            public boolean ActionOnUse(PlayerEntity player) {
                return false;
            }
        };
        if (spellID != null) {
            spell = SpellData.getSpellFromID(spellID);
        }
        // Add Spell Name to tooltip
        tooltip.add(Text.literal("§7" + spell.getBookName()));
        // Break up into lines of 24
        for (String line : KoffeeStringTools.splitIntoLines(spell.getDescription(), 24)) {
            // Add Broken Up Description to Tooltip
            tooltip.add(Text.literal("§8" + line));
        }
        // Add Cooldown to Tooltip
        tooltip.add(Text.literal("§9Cooldown: §9" + String.format("%.1f", (float) spell.getCooldown()/20F) + " sec"));
        // Add Mana Cost to Tooltip
        tooltip.add(Text.literal("§9Mana Cost: " + spell.getManaCost()));
        // Add the Tooltip to the item with the super method
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.YELLOW);
    }
}
