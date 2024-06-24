package koffee.magictoffee.item;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.block.ModBlocks;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.SpellRegisterer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MAGICTOFFEE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MagicToffee.MOD_ID, "magictoffee"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.magictoffee"))
                    .icon(() -> new ItemStack(ModItems.wand)).entries(((displayContext, entries) -> {
                        // Wand
                        entries.add(ModItems.wand);

                        // Spells
                        for (Spell spell : SpellRegisterer.spells) {
                            if (spell.getRegisterBook()) {
                                ItemStack stack = spell.getSpellBook();
                                stack.setCount(1);
                                entries.add(stack);
                            }
                        }

                        // Spellcaster
                        entries.add(ModBlocks.spellcaster);

                    })).build());

    public static void registerItemGroups() {
        MagicToffee.LOGGER.info("Registering Item Groups for Magic Toffee");
    }
}
