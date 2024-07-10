package koffee.magictoffee.listeners;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class PlayerDeathListener {

    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            SpellData.setMana(newPlayer, SpellData.getManaCap(newPlayer));
            Spell_ListS2CPacket.send(newPlayer);
            MagicToffee.refreshManaRegenTask(newPlayer);
        });
    }
}