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
        int extra = 1;
        for (int i = 0; i < 5; i++) {
            extra++;
            if (extra>4) {extra=0;}
            ItemStack spellBook = SpellData.getSpellFromID(SpellData.getSpell(player, i)).getSpellBook();
            customInventory.setStack(i, spellBook);
            spellBook.setCount(1);
            this.addSlot(new SpellcasterSpellbookSlot(customInventory, i, 8, 1+extra*18, player));
        }
        if (!player.getWorld().isClient()) {
            Spell_ListS2CPacket.send(((ServerPlayerEntity) player));
        }

        // Mana Flask slots -- Don't actually do anything yet...
        int h = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SpellcasterManaFlaskSlot(customInventory, 5+h, 116 + (j*18), 1+(i*18)));
                h++;
            }
        }

        // Add player inventory slots
        int m;
        int l;

        // Player Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 107 + m * 18));
            }
        }

        // Player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 165));
        }

    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {

    }
}