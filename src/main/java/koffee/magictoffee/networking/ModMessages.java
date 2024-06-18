package koffee.magictoffee.networking;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.networking.packet.Change_SpellC2SPacket;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier CHANGE_SPELL = new Identifier(MagicToffee.MOD_ID, "change_spell");
    public static final Identifier SPELL_LIST = new Identifier(MagicToffee.MOD_ID, "spell_list");

    // Client to Server Packets
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CHANGE_SPELL, Change_SpellC2SPacket::receive);
    }

    // Server to Client Packets
    public static void registerS2CPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SPELL_LIST, Spell_ListS2CPacket::receive);
    }

}
