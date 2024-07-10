package koffee.magictoffee.item;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.item.custom.ManaFlaskItem;
import koffee.magictoffee.item.custom.ManaModifier;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.item.custom.WandItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item wand = registerItem("wand", new WandItem(new FabricItemSettings().maxCount(1)));

    public static final Item spellbook = registerItem("spellbook", new SpellBook(new FabricItemSettings().maxCount(1)));

    public static final Item mana_expander = registerItem("mana_expander", new ManaModifier(new FabricItemSettings().maxCount(1)));

    public static final Item mana_catalyst = registerItem("mana_catalyst", new ManaModifier(new FabricItemSettings().maxCount(1)));

    public static final Item mana_petal = registerItem("mana_petal", new Item(new FabricItemSettings()));

    public static final Item mana_flask = registerItem("mana_flask", new ManaFlaskItem(new FabricItemSettings().maxCount(1)));

    public static final Item glass_flask = registerItem("glass_flask", new Item(new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicToffee.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MagicToffee.LOGGER.info("Registering Mod Items For Magic Toffee");
    }
}
