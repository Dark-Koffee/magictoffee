package koffee.magictoffee;

import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.block.entity.ModBlockEntities;
import koffee.magictoffee.commands.ManaCommand;
import koffee.magictoffee.commands.SpellsCommand;
import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.enchantments.ModEnchantments;
import koffee.magictoffee.event.WandAttackHandler;
import koffee.magictoffee.item.ModItemGroups;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.listeners.PlayerDeathListener;
import koffee.magictoffee.networking.ModMessages;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.screen.SpellcasterScreenHandler;
import koffee.magictoffee.spells.ModSpells;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MagicToffee implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "magictoffee";
	public static final Logger LOGGER = LoggerFactory.getLogger("MagicToffee");

	// Screen Handler (Spellcaster GUI)
	public static final ScreenHandlerType<SpellcasterScreenHandler> SPELLCASTER_SCREEN_HANDLER_SCREEN_HANDLER_TYPE;

	// Particles
	public static final DefaultParticleType HEART = FabricParticleTypes.simple();

	// Spellcaster Screen Handler
	static {
		SPELLCASTER_SCREEN_HANDLER_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(
				new Identifier(MOD_ID, "spellcaster"), SpellcasterScreenHandler::new
		);
	}

	// manaRegen variables
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static final Map<ServerPlayerEntity, ScheduledFuture<?>> playerTasks = new HashMap<>();


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Items and Item Groups
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		// Blocks
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		// Spells
		ModSpells.registerModSpells();

		// Wand Handlers
		WandAttackHandler.register();

		// Networking
		ModMessages.registerC2SPackets();

		// Enchantments
		ModEnchantments.registerModEnchantments();

		// Particles
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "heart"), HEART);

		// Commands
		CommandRegistrationCallback.EVENT.register(SpellsCommand::register);
		CommandRegistrationCallback.EVENT.register(ManaCommand::register);

		// Components
		ModComponents.register();


		// Register the player death event listener
		PlayerDeathListener.register();

		// Register the event listener for player join
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			Spell_ListS2CPacket.send(player);
			LOGGER.info(player.getName().getString() + " successfully sent Spell Packets on Join");

			// Register mana regen
			scheduleManaRegenTask(player);
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			cancelManaRegenTask(player);
		});
	}

	private static void scheduleManaRegenTask(ServerPlayerEntity player) {
		MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
		int manaRegen = magicComponent.getManaRegen();
		if (manaRegen > 0) {
			long delay = 1000 / manaRegen; // Calculate delay based on manaRegen
			Runnable task = () -> {
				if (player.isAlive()) {
					int mana = magicComponent.getMana();
					int manaCap = magicComponent.getManaCap();
					if (mana < manaCap) {
						magicComponent.setMana(mana + 1);
						Spell_ListS2CPacket.send(player);
					}
				}
			};

			ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
			playerTasks.put(player, scheduledTask);
		}
	}

	private static void cancelManaRegenTask(ServerPlayerEntity player) {
		ScheduledFuture<?> scheduledTask = playerTasks.remove(player);
		if (scheduledTask != null) {
			scheduledTask.cancel(true);
		}
	}

	public static void refreshManaRegenTask(ServerPlayerEntity player) {
		cancelManaRegenTask(player);
		scheduleManaRegenTask(player);
	}
}