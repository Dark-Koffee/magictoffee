package koffee.magictoffee.screen;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.util.SpellData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpellcasterManaFlaskSlot extends Slot {
    private final PlayerEntity player;
    private final Inventory inventory;
    public SpellcasterManaFlaskSlot(Inventory playerInventory, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(playerInventory, index, xPosition, yPosition);
        this.player = player;
        this.inventory = playerInventory;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isManaModifier(stack);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        updateManaModifier(player);
    }
    @Override
    public void onQuickTransfer(ItemStack newItem, ItemStack original) {
        super.setStack(newItem, original);
        updateManaModifier(player);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);
        updateManaModifier(player);
    }

    private void updateManaModifier(PlayerEntity player) {
        int manaRegen = 1;
        int manaCap = 100;
        // 5-10 Mana
        // Loop through all the mana modifier slots
        for (int i = 5; i <= 10; i++) {
            ItemStack stack = inventory.getStack(i);
            if (isManaCatalyst(stack)) {
                manaRegen++;
            }
            else if (isManaExpander(stack)) {
                manaCap += 25;
            }
        }
        SpellData.setManaRegen(player, manaRegen);
        SpellData.setManaCap(player, manaCap);
        // Set player's mana to mana cap if it's more
        if (SpellData.getMana(player) > manaCap) {
            SpellData.setMana(player, manaCap);
        }
        // Send spell list packets
        if (!player.getWorld().isClient()) {
            Spell_ListS2CPacket.send((ServerPlayerEntity) player);
        }
    }

    private static boolean isManaCatalyst(ItemStack stack) {
        return ItemStack.areItemsEqual(stack, ModItems.mana_catalyst.getDefaultStack());
    }
    private static boolean isManaExpander(ItemStack stack) {
        return ItemStack.areItemsEqual(stack, ModItems.mana_expander.getDefaultStack());
    }

    // Return true if it's a mana catalyst or expander, false otherwise.
    public static boolean isManaModifier(ItemStack stack) {
        return (isManaCatalyst(stack) || isManaExpander(stack));
    }
}
