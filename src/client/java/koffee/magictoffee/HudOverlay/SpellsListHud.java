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
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class SpellsListHud implements HudRenderCallback {

    private final Identifier[] textureIdentifiers = {
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top_left.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_top_right.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_middle.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom_left.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom.png"),
            new Identifier(MagicToffee.MOD_ID, "textures/hud/hud_bottom_right.png")
    };

    private final int[] textureWidths = new int[textureIdentifiers.length];
    private final int[] textureHeights = new int[textureIdentifiers.length];

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        assert client.player != null;
        if (WandAttackHandler.isWandInHand(client.player, Hand.MAIN_HAND)) {
            TextRenderer renderer = client.textRenderer;

            // Binds all the textures
            for (Identifier textureIdentifier : textureIdentifiers) {
                client.getTextureManager().bindTexture(textureIdentifier);
            }

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

            // Draw all the spells
            for (int i = 2; i >= -2; i--) {
                // Calculate the spell's index, wrapping around if necessary
                // (Adding 5 so when it's never negative)
                int spellIndex = (selected + i + 5) % 5;
                String spellID = ClientPacketHandler.spells[spellIndex];
                Spell spell = SpellData.getSpellFromID(spellID);




                // Gets the spell's display name including index temporarily
                String text = spell.getDisplayName() + " " + (spellIndex + 1);

                // Colors the spell's name depending on whether it's selected or not
                String prefix = "§8"; // Default Spell Color
                String starPrefix = "§8"; // Default Star Color
                if (i == 0) {
                    prefix = "§7"; // Selected Spell Color
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


            for (int i = 0; i <= 4; i++) {

                // Calculate the x and y coordinates to render the text in the bottom right corner
                int x = screenWidth - textWidth - 5; // 5 pixels from the right edge
                int y = screenHeight - ( textHeight * (i) * 4 / 3) - textHeight - 5; // 5 pixels from the bottom edge

                drawContext.drawText(renderer, spellsList[i], x, y, 0xffffff, true);
            }
        }
    }

    private void getTextureDimensions() {
        MinecraftClient client = MinecraftClient.getInstance();

        for (int i = 0; i < textureIdentifiers.length; i++)
        {
            NativeImageBackedTexture texture = (NativeImageBackedTexture) client.getTextureManager()
                    .getTexture(textureIdentifiers[i]);
            if (texture != null) {
                NativeImage image = texture.getImage();
                if (image != null) {
                    textureWidths[i] = image.getWidth();
                    textureHeights[i] = image.getHeight();
                }
            }
        }
    }

}
