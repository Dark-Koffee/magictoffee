package koffee.magictoffee;

import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.block.entity.ModBlockEntities;
import koffee.magictoffee.commands.SpellsCommand;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.enchantments.ModEnchantments;
import koffee.magictoffee.event.WandAttackHandler;
import koffee.magictoffee.item.ModItemGroups;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.networking.ModMessages;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.spells.ModSpells;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicToffee implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "magictoffee";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


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

		// Register the event listener for player join
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			Spell_ListS2CPacket.send(handler.getPlayer());
		});

		// Commands
		CommandRegistrationCallback.EVENT.register(SpellsCommand::register);

		// Components
		ModComponents.register();
	}
}