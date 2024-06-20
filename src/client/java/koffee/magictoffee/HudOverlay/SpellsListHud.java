package koffee.magictoffee.HudOverlay;

import koffee.magictoffee.client.ClientPacketHandler;
import koffee.magictoffee.event.WandAttackHandler;
import koffee.magictoffee.util.SpellData;
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


            // Obtain the screen width and height
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();

            // Calculate the height of the text
            int textHeight = renderer.fontHeight;

            int selected = ClientPacketHandler.selectedSpell;

            for (int i = 4; i >= 0; i--) {
                // Gets the spell's ID
                int num = i+3+selected;
                num = num%5;
                String spell = ClientPacketHandler.spells[num];
                // Gets the spell's display name including offset
                String text = "Spell Name";
                text = SpellData.getSpellName(spell) + String.valueOf(num);
                // Colors the spell's name depending on whether it's selected or not
                String prefix = "\u00A78";
                if (i == 2) {
                    prefix = "\u00A77";
                }
                // Calculate the width and height of the text
                int textWidth = renderer.getWidth(text);

                // Calculate the x and y coordinates to render the text in the bottom right corner
                int x = screenWidth - textWidth - 5; // 5 pixels from the right edge
                int y = screenHeight - ( textHeight * (i) * 4 / 3) - textHeight - 5; // 5 pixels from the bottom edge

                drawContext.drawText(renderer, prefix + text, x, y, 0xffffff, true);

            }
        }
    }
}
