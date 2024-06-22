package koffee.magictoffee.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import koffee.magictoffee.MagicToffee;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<MagicComponent> SPELLS_COMPONENT_KEY = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MagicToffee.MOD_ID, "spells"), MagicComponent.class);

    public static void register() {}

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, SPELLS_COMPONENT_KEY)
                .respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
                .end(MagicComponentImpl::new);
    }
}
