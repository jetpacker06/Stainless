package com.jetpacker06.stainless.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.stream.Stream;

import static com.jetpacker06.stainless.block.AlloyBlasterBlock.FACING;

public class DoubloonPileBlock extends Block {
    public static Optional<VoxelShape> VOXEL_SHAPE = Optional.of(Stream.of(
            Block.box(3, 0, 1, 5, 16, 15),
            Block.box(11, 0, 1, 13, 16, 15),
            Block.box(2, 0, 2, 3, 16, 14),
            Block.box(14, 0, 3, 15, 16, 13),
            Block.box(1, 0, 3, 2, 16, 13),
            Block.box(15, 0, 5, 16, 16, 11),
            Block.box(0, 0, 5, 1, 16, 11),
            Block.box(13, 0, 2, 14, 16, 14),
            Block.box(5, 0, 0, 11, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get());

    public DoubloonPileBlock(Properties p_49795_) {
        super(p_49795_);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return VOXEL_SHAPE.orElse(Shapes.block());
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return VOXEL_SHAPE.orElse(Shapes.block());
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
