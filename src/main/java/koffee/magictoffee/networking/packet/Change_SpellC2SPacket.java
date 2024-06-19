package koffee.magictoffee.networking.packet;

import koffee.magictoffee.item.custom.WandItem;
import koffee.magictoffee.util.IEntityDataSaver;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class Change_SpellC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // This is called when a packet is received by the server from the client
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof WandItem) {

            if (player.isSneaking()) {
                int test = SpellData.addSelected((IEntityDataSaver) player, -1);
                player.sendMessage(Text.literal("Change spell back" + String.valueOf(test)), true);
            } else {
                int test = SpellData.addSelected((IEntityDataSaver) player, 1);
                player.sendMessage(Text.literal("Change spell" + String.valueOf(test)), true);
            }
        }
    }
}
