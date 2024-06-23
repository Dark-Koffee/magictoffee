package koffee.magictoffee.damage;

import koffee.magictoffee.MagicToffee;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> MAGICMISSILE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MagicToffee.MOD_ID));

}
