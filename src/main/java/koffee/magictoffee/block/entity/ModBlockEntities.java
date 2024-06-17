package koffee.magictoffee.block.entity;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<SpellcasterBlockEntity> SPELLCASTER_BLOCK_ENTITY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MagicToffee.MOD_ID, "spellcaster_block_entity"),
                    FabricBlockEntityTypeBuilder.create(SpellcasterBlockEntity::new,
                            ModBlocks.spellcaster).build());
    public static void registerBlockEntities() {
        MagicToffee.LOGGER.info("Registering Block Entities for MagicToffee");
    }

}
