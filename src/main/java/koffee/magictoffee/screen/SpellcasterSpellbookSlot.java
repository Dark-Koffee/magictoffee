package koffee.magictoffee.screen;

import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.spells.SpellRegisterer;
import koffee.magictoffee.util.SpellData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpellcasterSpellbookSlot extends Slot {
    private final PlayerEntity player;
    public SpellcasterSpellbookSlot(Inventory playerInventory, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(playerInventory, index, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        //ModItems.wand.getDefaultStack()
        return isSpellbook(stack);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        updateSpellData(stack);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);
        updateSpellData(ItemStack.EMPTY);
    }

    private void updateSpellData(ItemStack stack) {
            String newSpell = SpellBook.getSpell(stack);
            SpellData.setSpell(player, this.getIndex(), newSpell != null ? newSpell : "magictoffee:empty");
        if (!player.getWorld().isClient()) {
            Spell_ListS2CPacket.send((ServerPlayerEntity) player);
        }
    }

    public static boolean isSpellbook(ItemStack stack) {
        String spellID = SpellBook.getSpell(stack);
        if (spellID != null) {
            // Loop registered spells
            for (String loopSpellID : SpellRegisterer.spellList()) {
                if (loopSpellID.equals(spellID)) {
                    return true;
                }
            }
        }
        return false;
    }
}
