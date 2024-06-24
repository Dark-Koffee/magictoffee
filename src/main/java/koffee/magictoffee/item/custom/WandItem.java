package koffee.magictoffee.item.custom;

import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.util.SpellData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandItem extends Item {
    public WandItem(Settings settings) {
        super(settings);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Right click to cast"));
        tooltip.add(Text.literal("§7Left click to cycle"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
