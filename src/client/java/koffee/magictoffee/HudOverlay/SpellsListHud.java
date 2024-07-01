package koffee.magictoffee.HudOverlay;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.client.ClientPacketHandler;
import koffee.magictoffee.event.WandAttackHandler;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class SpellsListHud implements HudRenderCallback {

    // spellcaster background
    private static final Identifier[] textureIdentifiers = {
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top_left.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top_right.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_middle_left.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_middle.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_middle_right.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom_left.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom_right.png"),
    };

    // Mana Bar Full
    private static final Identifier MANA_BAR_FULL = new Identifier(MagicToffee.MOD_ID, "textures/hud/mana_bar_full.png");

    // Mana Bar Empty
    private static final Identifier MANA_BAR_EMPTY = new Identifier(MagicToffee.MOD_ID, "textures/hud/mana_bar_empty.png");

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
            int textWidth = 0;

            int selected = ClientPacketHandler.selectedSpell;

            // Gets the player's magic component & spell cooldown, and current time
            PlayerEntity player = client.player;
            long currentTime = player.getWorld().getTime();

            // Spell List
            String[] spellsList = new String[5];

            // Get the text and order for all the spells
            for (int i = 2; i >= -2; i--) {
                // Calculate the spell's index, wrapping around if necessary
                // (Adding 5 so when it's never negative)
                int spellIndex = (selected + i + 5) % 5;
                String spellID = ClientPacketHandler.spells[spellIndex];
                Spell spell = SpellData.getSpellFromID(spellID);

                // Gets the spell's display name including index temporarily
                String text = spell.getDisplayName();// + " " + (spellIndex + 1);

                // Colors the spell's name depending on whether it's selected or not
                String prefix = "§7"; // Default Spell Color
                String starPrefix = "§7"; // Default Star Color
                if (i == 0) {
                    prefix = "§f"; // Selected Spell Color
                    starPrefix = "§f"; // Selected Star color
                }

                // Get last updated cooldown
                long lastUsed = ClientPacketHandler.spellCooldowns.getOrDefault(spellID, 0L);

                // Checks if spell is on cooldown
                if (currentTime >= lastUsed+spell.getCooldown()) {
                    prefix = starPrefix + "★ " + prefix;
                } else {prefix = starPrefix + "☆ " + prefix;}

                // Get the biggest width of text, so they're left aligned
                int tempWidth = renderer.getWidth(prefix + text);
                if (tempWidth > textWidth) {textWidth = tempWidth;}

                spellsList[(i-2)*-1] = prefix + text;
            }

            int xAlign = 4;

            // Draw the GUI behind the spells
            int defaultHeight = screenHeight - ( textHeight * 16 / 3) - textHeight - 11;
            // Top left
            drawContext.drawTexture(textureIdentifiers[0],
                    screenWidth - textWidth - 25 - xAlign,
                    defaultHeight,
                    0, 0, 64, 64, 64, 64);
            // Top middle
            for (int i = 0; i < (textWidth-6)/6; i++) {
                drawContext.drawTexture(textureIdentifiers[1],
                        screenWidth - textWidth - 37 + 6*i - xAlign,
                        defaultHeight,
                        0, 0, 64, 64, 64, 64);
            }
            // Top right
            drawContext.drawTexture(textureIdentifiers[2],
                    screenWidth - textWidth - 55 + ((textWidth-6)/6)*6 - xAlign,
                    defaultHeight,
                    0, 0, 64, 64, 64, 64);
//            System.out.println("Screen Width:" + (screenWidth));
//            System.out.println("Top Right X:" + (screenWidth - textWidth + 37 -64 + ((textWidth-6)/6)*6));
            // Middle left
            for (int i = 0; i < (screenHeight - defaultHeight-12) / 12; i++) {
                drawContext.drawTexture(textureIdentifiers[3],
                        screenWidth - textWidth - 25 - xAlign,
                        defaultHeight - 18 + 12 * i,
                        0, 0, 64, 64, 64, 64);
            }
            // Middle
            for (int i = 0; i < (screenHeight - defaultHeight-12) / 12; i++) {
                for (int j = 0; j < (textWidth - 6) / 6; j++) {
                    drawContext.drawTexture(textureIdentifiers[4],
                            screenWidth - textWidth - 37 + 6 * j - xAlign,
                            defaultHeight - 18 + 12 * i,
                            0, 0, 64, 64, 64, 64);
                }
            }
            // Middle right
            for (int i = 0; i < (screenHeight - defaultHeight-12) / 12; i++) {
                drawContext.drawTexture(textureIdentifiers[5],
                        screenWidth - textWidth - 55 + ((textWidth-6)/6)*6 - xAlign,
                        defaultHeight - 18 + 12 * i,
                        0, 0, 64, 64, 64, 64);
            }
            // Bottom left
            drawContext.drawTexture(textureIdentifiers[6],
                    screenWidth - textWidth - 25 - xAlign,
                    defaultHeight -59 + 12 * (((screenHeight - defaultHeight-12) / 12) + 1),
                    0, 0, 64, 64, 64, 64);
            // Bottom middle
            for (int i = 0; i < (textWidth-6)/6; i++) {
                drawContext.drawTexture(textureIdentifiers[7],
                        screenWidth - textWidth - 37 + 6*i - xAlign,
                        defaultHeight -59 + 12 * (((screenHeight - defaultHeight-12) / 12) + 1),
                        0, 0, 64, 64, 64, 64);
            }
            // Bottom right
            drawContext.drawTexture(textureIdentifiers[8],
                    screenWidth - textWidth - 55 + ((textWidth-6)/6)*6 - xAlign,
                    defaultHeight -59 + 12 * (((screenHeight - defaultHeight-12) / 12) + 1),
                    0, 0, 64, 64, 64, 64);


            // Display mana
            String manaText = "§9Mana: §b" + (ClientPacketHandler.mana) + "/" + (ClientPacketHandler.manaCap);
            drawContext.drawText(renderer, manaText,
                    (screenWidth-(textWidth + 25 + xAlign)) + ((textWidth + 25 + xAlign) / 2) - (renderer.getWidth(manaText) / 2),
                    defaultHeight-textHeight-4,
                    0xffffff, true);

            // Display mana bar full
            drawContext.drawTexture(MANA_BAR_FULL,
                    screenWidth - textWidth - 25 - 15 - 5 - xAlign,
                    defaultHeight + 1,
                    0, 0, 64, 64, 64, 64);

            // Display mana bar empty
            drawContext.drawTexture(MANA_BAR_EMPTY,
                    screenWidth - textWidth - 25 - 15 - 5 - xAlign,
                    defaultHeight + 1,
                    0, 0,
                    64,
                    Math.min((55 -((int) (55F * (((float) ClientPacketHandler.mana) / ClientPacketHandler.manaCap)))) + 4, 64),
                    64, 64);

            // Draw all the spells
            for (int i = 0; i <= 4; i++) {

                // Calculate the x and y coordinates to render the text in the bottom right corner
                int x = screenWidth - textWidth - 15 - xAlign; // Math
                int y = screenHeight - ( textHeight * (i) * 4 / 3) - textHeight - 6; // More math

                drawContext.drawText(renderer, spellsList[i], x, y, 0xffffff, true);
            }
        }
    }

}
