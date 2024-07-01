package koffee.magictoffee.item.custom;

import koffee.magictoffee.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaModifier extends Item {
    public ManaModifier(Settings settings) {
        super(settings);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Used in the Spellcaster"));
        // Some may ask if this is better than just making two items. -\_(-_-)_/-     (Best I could do with my keyboard)
        // Mana Expander
        if (this.getTranslationKey(stack).equals(ModItems.mana_expander.getTranslationKey())) {//"item.magictoffee.mana_expander")) {
            tooltip.add(Text.literal("§7Increases your mana"));
            tooltip.add(Text.literal("§7cap by 25"));
        }
        // Mana Catalyst
        else if (this.getTranslationKey(stack).equals(ModItems.mana_catalyst.getTranslationKey())) {//"item.magictoffee.mana_catalyst")) {
            tooltip.add(Text.literal("§7Increases your mana"));
            tooltip.add(Text.literal("§7regeneration by 25%"));

        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.LIGHT_PURPLE);
    }
}
