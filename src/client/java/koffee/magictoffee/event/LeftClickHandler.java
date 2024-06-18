package koffee.magictoffee.event;

import koffee.magictoffee.item.custom.WandItem;
import koffee.magictoffee.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class LeftClickHandler {
    // Var for if left click was pressed last time we checked
    private static boolean wasLeftClickPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Make sure the player exists?
            if (client.player == null) {
                return;
            }

            // Var for if left click is being pressed this tick
            boolean  isLeftClickPressed = client.options.attackKey.isPressed();

            // Check if it is being pressed and it wasn't before
            if (isLeftClickPressed && !wasLeftClickPressed) {
                // Send
                handleLeftClick(client);
            }

            wasLeftClickPressed = isLeftClickPressed;
        });
    }

    private static void handleLeftClick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        assert player != null;
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof WandItem) {
            player.sendMessage(Text.literal("You left clicked wand"), true);

            ClientPlayNetworking.send(ModMessages.CHANGE_SPELL, PacketByteBufs.create());
        }
    }
}
