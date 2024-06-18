package koffee.magictoffee.spells;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.block.SpellcasterBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModSpells {

    // TestSpell
    public static final TestSpell testSpell = new TestSpell();

    private static Spell registerSpell(String name, Block block) {
        RegisterSpells
    }

    public static void registerModSpells() {
        MagicToffee.LOGGER.info("Registering Mod Spells For Magic Toffee");
    }
}
