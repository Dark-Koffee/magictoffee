package koffee.magictoffee.item;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.item.custom.WandItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item wand = registerItem("wand", new WandItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicToffee.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MagicToffee.LOGGER.info("Registering Mod Items For Magic Toffee");
    }
}
