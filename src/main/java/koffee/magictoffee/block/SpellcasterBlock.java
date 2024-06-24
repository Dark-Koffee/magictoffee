package koffee.magictoffee.block;

import koffee.magictoffee.block.entity.ModBlockEntities;
import koffee.magictoffee.block.entity.SpellcasterBlockEntity;
import koffee.magictoffee.screen.SpellcasterScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpellcasterBlock extends BlockWithEntity implements BlockEntityProvider {

    public SpellcasterBlock(Settings settings) {
        super(settings);
    }

    // Supposedly defines the voxelshape
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    // Sets the render type to model so the block is visible
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // Action on right click
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            //player.sendMessage(Text.literal("§8[§7§oInsert opening of gui here§8]"), false);
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inv, p) -> new SpellcasterScreenHandler(syncId, inv), Text.of("")));
        }

        return ActionResult.SUCCESS;
    }
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpellcasterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.SPELLCASTER_BLOCK_ENTITY_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
    
}
