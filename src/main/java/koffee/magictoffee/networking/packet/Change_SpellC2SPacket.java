package koffee.magictoffee.networking.packet;

import koffee.magictoffee.item.custom.WandItem;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class Change_SpellC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // This is called when a packet is received by the server from the client
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof WandItem) {

            if (player.isSneaking()) {
                int test = SpellData.addSelected(player, -1);
                player.sendMessage(Text.literal("§7Change spell back" + (test + 1)), true);
            } else {
                int test = SpellData.addSelected(player, 1);
                player.sendMessage(Text.literal("§7Change spell" + (test + 1)), true);
            }
            player.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 2.0F);
            Spell_ListS2CPacket.send(player);
        }
    }
}
