package koffee.magictoffee.damage;

import koffee.magictoffee.MagicToffee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> MAGICMISSILE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MagicToffee.MOD_ID, "magicmissile"));

    public static DamageSource of(World world, RegistryKey<DamageType> key, Entity attacker, Entity victim) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker, victim);
    }
}
