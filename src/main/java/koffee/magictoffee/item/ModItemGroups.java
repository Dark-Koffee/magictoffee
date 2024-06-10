package koffee.magictoffee.item;

import koffee.magictoffee.MagicToffee;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MAGICTOFFEE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MagicToffee.MOD_ID, "wand"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.magictoffee"))
                    .icon(() -> new ItemStack(ModItems.wand)).entries(((displayContext, entries) -> {
                        entries.add(ModItems.wand);


                    })).build());

    public static void registerItemGroups() {
        MagicToffee.LOGGER.info("Registering Item Groups for Magic Toffee");
    }
}
