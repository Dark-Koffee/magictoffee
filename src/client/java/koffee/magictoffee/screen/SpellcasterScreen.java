package koffee.magictoffee.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import koffee.magictoffee.MagicToffee;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpellcasterScreen extends HandledScreen<SpellcasterScreenHandler> {
    private static final Identifier PLAYER_INVENTORY_TEXTURE = new Identifier(MagicToffee.MOD_ID, "textures/gui/container/spellcaster.png");



    public SpellcasterScreen(SpellcasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int X = (this.width - this.backgroundWidth) / 2 + (this.backgroundWidth / 2) - 88;
        int Y = (this.height - this.backgroundHeight) / 2 + (this.backgroundHeight / 2) - 90;

        // Bind & Draw the player's inventory background
        RenderSystem.setShaderTexture(0, PLAYER_INVENTORY_TEXTURE);
        context.drawTexture(PLAYER_INVENTORY_TEXTURE, X, Y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
//        super.drawForeground(context, mouseX, mouseY);
        // Move the "Inventory" text down
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, (this.backgroundHeight/2) -32, 4210752, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Initialize GUI components
        this.backgroundHeight = 256;
    }
}
