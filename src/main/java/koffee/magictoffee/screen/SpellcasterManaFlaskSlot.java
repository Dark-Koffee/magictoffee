package koffee.magictoffee.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class SpellcasterManaFlaskSlot extends Slot {
    public SpellcasterManaFlaskSlot(Inventory playerInventory, int index, int xPosition, int yPosition) {
        super(playerInventory, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return ItemStack.areItemsEqual(stack, Items.POTION.getDefaultStack());
    }
}
