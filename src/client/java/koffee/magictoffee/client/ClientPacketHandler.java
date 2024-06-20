package koffee.magictoffee.client;

import koffee.magictoffee.networking.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientPacketHandler {
    public static String[] spells = new String[5];
    public static int selectedSpell;
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.SPELL_LIST, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                ClientPlayerEntity player = client.player;
                for (int i = 0; i < 5; i++) {
                    // 32767 is the maximum string length in Minecraft
                    spells[i] = buf.readString(32767);
                }
                selectedSpell = buf.readInt();
            });
        });
    }
}
