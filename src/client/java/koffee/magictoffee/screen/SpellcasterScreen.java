package koffee.magictoffee.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpellcasterScreen extends HandledScreen<SpellcasterScreenHandler> {
    private static final Identifier PLAYER_INVENTORY_TEXTURE = new Identifier("minecraft", "textures/gui/container/inventory.png");
    private static final Identifier PLAYER_INV = new Identifier("minecraft", "textures/gui/container/inventory.png");
//    private static final Identifier SPELLCASTER_TEXTURE = new Identifier(MagicToffee.MOD_ID, "textures/gui/container/spellcaster.png");
    private static final int SPELLCASTER_SIZE = 176;



    public SpellcasterScreen(SpellcasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        // Bind the spellcaster texture and find location
//        RenderSystem.setShaderTexture(0, SPELLCASTER_TEXTURE);
        int X = (this.width - this.backgroundWidth) / 2 + (this.backgroundWidth / 2) - 88;
        int Y = (this.height - this.backgroundHeight) / 2 + (this.backgroundHeight / 2) - 83;

        // Bind & Draw the player's inventory background
        RenderSystem.setShaderTexture(0, PLAYER_INVENTORY_TEXTURE);
        context.drawTexture(PLAYER_INVENTORY_TEXTURE, X, Y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Bind & Draw the player's inventory background
        RenderSystem.setShaderTexture(0, PLAYER_INVENTORY_TEXTURE);
        context.drawTexture(PLAYER_INVENTORY_TEXTURE, X, Y, 0, 0, this.backgroundWidth, this.backgroundHeight);


//        // Bind the player inventory texture
//        RenderSystem.setShaderTexture(0, PLAYER_INVENTORY_TEXTURE);
//
//        // Draw the player inventory background
//
//        // Draw the player's inventory slots
//        context.drawTexture(PLAYER_INVENTORY_TEXTURE, x + 7, y + 83, 7, 83, 162, 76); // The exact positions and dimensions might need adjustment
    }

//    private void drawPlayerInventory(DrawContext context, int x, int y) {
//    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);

        // Calculate the position for the spellcaster
        int centerX = (this.width - this.backgroundWidth) / 2;
        int centerY = (this.height - this.backgroundHeight) / 2;
        int x = centerX + (this.backgroundWidth / 2) - (SPELLCASTER_SIZE/2);
        int y = centerY + (this.backgroundHeight / 2) - (SPELLCASTER_SIZE/2);

        // Draw the spellcaster
//        context.drawTexture(SPELLCASTER_TEXTURE, x, y, 0, 0, SPELLCASTER_SIZE, SPELLCASTER_SIZE);
//        drawPlayerInventory(context, x, y + centerY + 10); // Offset the inventory by 10 pixels below the spellcaster texture
    }

    @Override
    protected void init() {
        super.init();
        // Initialize GUI components
    }
}
