package koffee.magictoffee.networking.packet;

import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.networking.ModMessages;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class Spell_ListS2CPacket {
    public static void send(ServerPlayerEntity player) {
        // Create buf
        PacketByteBuf buf = PacketByteBufs.create();
        // Write spells slots
        for (int i = 0; i < 5; i++) {
            buf.writeString(SpellData.getSpell(player, i), 32767);
        }
        // Write selected slot
        buf.writeInt(SpellData.getSelected(player));
        // Get player's magic component
        MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        // Write player's mana
        buf.writeInt(magicComponent.getMana());
        // Write player's manaCap
        buf.writeInt(magicComponent.getManaCap());
        // Write spell cooldowns
        magicComponent.writeCooldownsToBuf(buf);
        // Send the packet
        ServerPlayNetworking.send(player, ModMessages.SPELL_LIST, buf);
    }
}
