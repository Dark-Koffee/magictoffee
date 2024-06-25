package koffee.magictoffee.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.client.ClientPacketHandler;
import koffee.magictoffee.util.SpellData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpellcasterScreen extends HandledScreen<SpellcasterScreenHandler> {
    private static final Identifier SPELLCASTER_IMAGE = new Identifier(MagicToffee.MOD_ID, "textures/gui/container/spellcaster.png");



    public SpellcasterScreen(SpellcasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 196;
        System.out.println(super.playerInventoryTitleY);
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
//        // Bind & Draw the player's inventory background
        RenderSystem.setShaderTexture(0, SPELLCASTER_IMAGE);
        context.drawTexture(SPELLCASTER_IMAGE,
                (width - backgroundWidth) / 2, // X
                (height - backgroundHeight) / 2, // Y
                0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
//        super.drawForeground(context, mouseX, mouseY);
        // Move the "Inventory" text down
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040, false);

        // Draw the spell names
        for (int i=0; i <5; i++) {
            context.drawText(this.textRenderer, SpellData.getSpellFromID(ClientPacketHandler.spells[(i+3)%5]).getDisplayName(), 30, 13+i*18, 16777215, true);
        }
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
        // Actually, never use this never mind. No woriek
        //                  Intentional misspelling  ^^
        //                   Please leave it there   u
    }
}
