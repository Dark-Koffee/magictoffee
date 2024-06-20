package koffee.magictoffee.networking;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.networking.packet.Change_SpellC2SPacket;
import koffee.magictoffee.networking.packet.Hit_ResultC2SPacket;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    // Client to Server
    public static final Identifier CHANGE_SPELL = new Identifier(MagicToffee.MOD_ID, "change_spell");
    public static final Identifier HIT_RESULT = new Identifier(MagicToffee.MOD_ID, "hit_result");
    // Server to Client
    public static final Identifier SPELL_LIST = new Identifier(MagicToffee.MOD_ID, "spell_list");

    // Client to Server Packets
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CHANGE_SPELL, Change_SpellC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(HIT_RESULT, Hit_ResultC2SPacket::receive);
    }

}
