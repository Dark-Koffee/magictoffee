package koffee.magictoffee.client;

import koffee.magictoffee.networking.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import java.util.HashMap;
import java.util.Map;

public class ClientPacketHandler {
    public static String[] spells = new String[5];
    public static int selectedSpell;
    public static final Map<String, Long> spellCooldowns = new HashMap<>();
    public static int mana = 100;
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.SPELL_LIST, (client, handler, buf, responseSender) -> client.execute(() -> {
            // Register spells
            for (int i = 0; i < 5; i++) {
                // 32767 is the maximum string length in Minecraft
                spells[i] = buf.readString(32767);
            }
            // Register selected
            selectedSpell = buf.readInt();
            // Register mana
            mana = buf.readInt();
            // Register cooldowns
            int size = buf.readInt(); // Read the size of the map
            for (int i = 0; i < size; i++) {
                String spellId = buf.readString(); // Read the spell ID
                long cooldown = buf.readLong(); // Read the cooldown
                spellCooldowns.put(spellId, cooldown); // Put the data into the map
            }
        }));
    }
}
