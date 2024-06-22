package koffee.magictoffee.networking.packet;

import koffee.magictoffee.networking.ModMessages;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class Spell_ListS2CPacket {
    public static void send(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        for (int i = 0; i < 5; i++) {
            buf.writeString(SpellData.getSpell(player, i), 32767);
        }
        buf.writeInt(SpellData.getSelected(player));
        ServerPlayNetworking.send(player, ModMessages.SPELL_LIST, buf);
    }
}
