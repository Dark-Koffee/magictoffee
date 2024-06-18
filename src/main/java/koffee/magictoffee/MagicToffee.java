package koffee.magictoffee;

import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.block.entity.ModBlockEntities;
import koffee.magictoffee.enchantments.FrostbiteEnchantment;
import koffee.magictoffee.enchantments.ModEnchantments;
import koffee.magictoffee.item.ModItemGroups;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.spells.ModSpells;
import net.fabricmc.api.ModInitializer;

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
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		// Blocks
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		// Spells
		ModSpells.registerModSpells();

		// Enchantments
		ModEnchantments.registerModEnchantments();
	}
}