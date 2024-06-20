package koffee.magictoffee;

import koffee.magictoffee.HudOverlay.SpellsListHud;
import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.client.ClientPacketHandler;
import koffee.magictoffee.event.LeftClickHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;

public class MagicToffeeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.spellcaster, RenderLayer.getCutout());

		LeftClickHandler.register();

		// Register Server to Client packets
		ClientPacketHandler.register();

		HudRenderCallback.EVENT.register(new SpellsListHud());
	}
}