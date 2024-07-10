package koffee.magictoffee;

import koffee.magictoffee.HudOverlay.SpellsListHud;
import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.client.ClientPacketHandler;
import koffee.magictoffee.event.LeftClickHandler;
import koffee.magictoffee.screen.SpellcasterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.particle.EmotionParticle;
import net.minecraft.client.render.RenderLayer;

public class MagicToffeeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// Register render map for Spellcaster
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.spellcaster, RenderLayer.getCutout());
		// Register render map for Mana Flower
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.mana_flower, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.potted_mana_flower, RenderLayer.getCutout());

		LeftClickHandler.register();

		// Register Server to Client packets
		ClientPacketHandler.register();

		// Register HUD
		HudRenderCallback.EVENT.register(new SpellsListHud());

		// Register Spellcaster Screen
		HandledScreens.register(MagicToffee.SPELLCASTER_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, SpellcasterScreen::new);

		// Register particles
		ParticleFactoryRegistry.getInstance().register(MagicToffee.HEART, EmotionParticle.HeartFactory::new);
	}
}