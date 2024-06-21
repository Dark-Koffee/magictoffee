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

            // Draw all the spells
            for (int i = 2; i >= -2; i--) {
                // Calculate the spell's index, wrapping around if necessary
                // (Adding 5 so when it's never negative)
                int spellIndex = (selected + i + 5) % 5;
                String spell = ClientPacketHandler.spells[spellIndex];

                // Gets the spell's display name including offset
                String text = SpellData.getSpellName(spell) + " " + (spellIndex + 1);

                // Colors the spell's name depending on whether it's selected or not
                String prefix = "§8"; // Default Color
                if (i == 0) {
                    prefix = "§7"; // Selected Spell Color
                }

                // Calculate the width and height of the text
                int textWidth = renderer.getWidth(text);

                // Calculate the x and y coordinates to render the text in the bottom right corner
                int x = screenWidth - textWidth - 5; // 5 pixels from the right edge
                int y = screenHeight - ( textHeight * ((i-2)*-1) * 4 / 3) - textHeight - 5; // 5 pixels from the bottom edge

                drawContext.drawText(renderer, prefix + text, x, y, 0xffffff, true);

            }
        }
    }
}
