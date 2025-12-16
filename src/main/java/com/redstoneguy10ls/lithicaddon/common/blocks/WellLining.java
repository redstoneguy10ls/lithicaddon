package com.redstoneguy10ls.lithicaddon.common.blocks;

import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WellLining extends Block implements IFluidLoggable {
    
    /*
            box(0, 0, 3, 3, 16, 13),
        box(13, 0, 3, 16, 16, 13),
        box(0, 0, 0, 16, 16, 3),
        box(0, 0, 13, 16, 16, 16)
     */
    private static final VoxelShape SHAPE = Shapes.or(
        box(0, 0, 2, 2, 16, 14),
        box(14, 0, 2, 16, 16, 14),
        box(0, 0, 0, 16, 16, 2),
        box(0, 0, 14, 16, 16, 16)
    );
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }
    
    public static final FluidProperty FLUID = TFCBlockStateProperties.ALL_WATER;
    public WellLining(Properties pProperties) {
        super(pProperties);
        registerDefaultState(getStateDefinition().any().setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)));
        
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(getFluidProperty());
    }
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return IFluidLoggable.super.getFluidState(state);
    }
    
    @Override
    public FluidProperty getFluidProperty() {
        return FLUID;
    }
}
