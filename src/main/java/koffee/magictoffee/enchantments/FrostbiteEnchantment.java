package koffee.magictoffee.enchantments;

import koffee.magictoffee.MagicToffee;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FrostbiteEnchantment extends Enchantment {
    // Constructor for the Frostbite enchantment
    public FrostbiteEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(weight, type, slot);
    }

    // The minimum level needed to see the enchantment in the enchantment table
    @Override
    public int getMinPower(int level) {
        return 1;
    }

    // The highest level of Frostbite is Frostbite III
    @Override
    public int getMaxLevel() {
        return 3;
    }

    // Upon attacking a living entity apply slowness for two seconds * the level to the target
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40 * level, level - 1));
        }

        super.onTargetDamaged(user, target, level);
    }

}


