package koffee.magictoffee.item.custom;

import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ManaFlaskItem extends Item {
    public ManaFlaskItem(Settings settings) {
        super(settings);
    }

    private static final int DRINK_DURATION = 32;

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = user instanceof PlayerEntity ? (PlayerEntity) user : null;

        if (!world.isClient) {
            applyManaEffects(player);
        }

        if (player != null) {
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (stack.isEmpty()) {
            return new ItemStack(ModItems.glass_flask);
        }

        if (player != null && !player.getAbilities().creativeMode) {
            if (!player.getInventory().insertStack(new ItemStack(ModItems.glass_flask))) {
                player.dropItem(new ItemStack(ModItems.glass_flask), false);
            }
        }

        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return DRINK_DURATION;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§9+50 Mana"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.AQUA);
    }

    // Method to apply mana-related effects
    private void applyManaEffects(PlayerEntity player) {
        MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        int mana = magicComponent.getMana()+25;
        int manaCap = magicComponent.getManaCap();
        if (mana > manaCap && mana <= manaCap+25) {
            magicComponent.setMana(manaCap);
        }
        else {
            magicComponent.setMana(mana);
        }
        Spell_ListS2CPacket.send((ServerPlayerEntity) player);
    }
}

