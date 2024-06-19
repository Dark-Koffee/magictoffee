package koffee.magictoffee.HudOverlay;

import koffee.magictoffee.event.WandAttackHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Hand;

public class SpellsListHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        assert client.player != null;
        if (WandAttackHandler.isWandInHand(client.player, Hand.MAIN_HAND)) {
            TextRenderer renderer = client.textRenderer;

            // The text to render
            String text = "Selected Spells Are here";

            // Obtain the screen width and height
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();

            // Calculate the width and height of the text
            int textWidth = renderer.getWidth(text);
            int textHeight = renderer.fontHeight;

            // Calculate the x and y coordinates to render the text in the bottom right corner
            int x = screenWidth - textWidth - 5; // 5 pixels from the right edge
            int y = screenHeight - textHeight - 5; // 5 pixels from the bottom edge

            drawContext.drawText(renderer, text, x, y, 0xffffff, true);
        }
    }
}
