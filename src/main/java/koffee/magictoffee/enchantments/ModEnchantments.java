package koffee.magictoffee.enchantments;

import koffee.magictoffee.MagicToffee;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static Enchantment FROSTBITE = register("frostbite",
            new FrostbiteEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(MagicToffee.MOD_ID, "frostbite"), enchantment);
    }

    public static void registerModEnchantments() {
        MagicToffee.LOGGER.info("Registering Enchantments for " + MagicToffee.MOD_ID);
    }
}
