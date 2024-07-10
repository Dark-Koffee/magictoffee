package koffee.magictoffee.block;

import koffee.magictoffee.MagicToffee;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // Spellcaster
    public static final Block spellcaster = registerBlock("spellcaster",
            new SpellcasterBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)
                    .nonOpaque()));

    // Mana Flower
    public static final Block mana_flower = registerBlock("mana_flower",
            new FlowerBlock(StatusEffects.SLOWNESS, 10,
                    FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));

    // Potted Mana Flower
    public static final Block potted_mana_flower = Registry.register(Registries.BLOCK, new Identifier(MagicToffee.MOD_ID, "potted_mana_flower"),
            new FlowerPotBlock(mana_flower, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MagicToffee.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MagicToffee.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        MagicToffee.LOGGER.info("Registering Mod Blocks For Magic Toffee");
    }
}
