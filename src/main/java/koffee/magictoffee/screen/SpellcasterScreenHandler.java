package koffee.magictoffee.screen;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.util.SpellData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpellcasterScreenHandler extends ScreenHandler {


    public SpellcasterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(MagicToffee.SPELLCASTER_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, syncId);
        PlayerEntity player = playerInventory.player;
        // Spell Book Slots
        SimpleInventory customInventory = new SimpleInventory(11);
        SpellData.setSelected(player, 0); // Sets their selected spell to 0
        for (int i = 0; i < 5; i++) {
            ItemStack spellBook = SpellData.getSpellFromID(SpellData.getSpell(player, i)).getSpellBook();
            customInventory.setStack(i, spellBook);
            spellBook.setCount(1);
            this.addSlot(new SpellcasterSpellbookSlot(customInventory, (i+3)%5, 8, 8 + i * 18, player));
        }
        if (!player.getWorld().isClient()) {
            Spell_ListS2CPacket.send(((ServerPlayerEntity) player));
        }

        // Mana Flask slots -- Don't actually do anything yet...
        int h = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SpellcasterManaFlaskSlot(customInventory, 5+h, 116 + (j*18), 8+(i*18)));
                h++;
            }
        }

        // Add player inventory slots
        addPlayerInventory(playerInventory, 28);
        addPlayerHotbar(playerInventory, 28);

    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {

        // 0-4 Spells
        // 5-10 Mana
        // 11-37 inventory
        // 38-46 hotbar

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot); // Gets the clicked slot

        if (slot2 != null && slot2.hasStack()) { // I'm aware intellij claims slot2 will never be null, leave it

            ItemStack itemStack2 = slot2.getStack(); // Gets clicked item stack
            itemStack = itemStack2.copy(); // Make a copy of the item stack to change

            // If it's a wand slot
            if (slot >= 0 && slot <= 4) {
                // Try to move it to the player's inventory
                if (!this.insertItem(itemStack2, 11, 47, true)) {
                    // If it fails, return empty
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            }
            // If the slot is a mana bottle slot
            else if (slot >= 5 && slot <= 10) {
                return ItemStack.EMPTY; // Return empty for the time being
            }
            // If it's an Inventory / Hotbar slot
            else {
                // If it's a spell book
                if (SpellcasterSpellbookSlot.isSpellbook(itemStack2)) {
                    // Try to move it to the spell slots
                    if (!this.insertItem(itemStack2, 0, 5, false)) {
                        // If it fails, return
                        return ItemStack.EMPTY;
                    }
                }
                // If it's a mana bottle - Clearly doesn't work yet or check for
                // the right thing, because mana bottles don't exist yet -_-
                else if (SpellcasterSpellbookSlot.isSpellbook(itemStack2)) {
                    // Try to move it to the mana slots
                    if (!this.insertItem(itemStack2, 5, 11, false)) {
                        // If it fails, return
                        return ItemStack.EMPTY;
                    }
                }
                // If it's the inventory
                else if (slot >= 11 && slot <= 37) {
                    // Try to move it to the hotbar
                    if (!this.insertItem(itemStack2, 38, 47, false)) {
                        // If it fails, return
                        return ItemStack.EMPTY;
                    }
                }
                // If it's the hotbar
                else if (slot >= 38 && slot <= 46) {
                    // Try to move it to the inventory
                    if (this.insertItem(itemStack2, 11, 38, false)) {
                        // If it fails, return
                        return ItemStack.EMPTY;
                    }
                }
            }

            // If the item stack is now empty after the transfer, clear the slot.
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                // Otherwise, mark the slot as dirty to update it.
                slot2.markDirty();
            }

            // If the item count did not change, return an empty item stack to indicate no transfer happened.
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            // Notify the slot that the player has taken the item.
            slot2.onTakeItem(player, itemStack2);

        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {

    }

    private void addPlayerInventory(PlayerInventory playerInventory, int offsetY) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18 + offsetY));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory, int offsetY) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144 + offsetY));
        }
    }
}