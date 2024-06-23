package koffee.magictoffee.screen;

import koffee.magictoffee.MagicToffee;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SpellcasterScreenHandler extends ScreenHandler {
    private final PlayerInventory playerInventory;


    public SpellcasterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(MagicToffee.SPELLCASTER_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, syncId);
        this.playerInventory = playerInventory;

        // Add slots for your custom container (e.g. for books)
        // this.addSlot(new Slot(customInventory, 0, 62, 17));

        // Add player inventory slots
        int m;
        int l;

        // Player Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        // Player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}