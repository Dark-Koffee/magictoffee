package koffee.magictoffee.block;

import koffee.magictoffee.MagicToffee;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // Spellcaster
    public static final Block spellcaster = registerBlock("spellcaster",
            new SpellcasterBlock(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).luminance(0)
                    .nonOpaque()));

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
