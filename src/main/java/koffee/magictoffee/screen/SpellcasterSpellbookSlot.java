package koffee.magictoffee.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class SpellcasterSpellbookSlot extends Slot {
    public SpellcasterSpellbookSlot(Inventory playerInventory, int index, int xPosition, int yPosition) {
        super(playerInventory, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        //ModItems.wand.getDefaultStack()
        return ItemStack.areItemsEqual(stack, Items.BOOK.getDefaultStack());
    }
}
