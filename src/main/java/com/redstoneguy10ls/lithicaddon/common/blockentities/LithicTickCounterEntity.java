package com.redstoneguy10ls.lithicaddon.common.blockentities;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LithicTickCounterEntity extends TickCounterBlockEntity {

    public static void reset(Level level, BlockPos pos)
    {
        level.getBlockEntity(pos, LithicBlockEntities.TICK_COUNTER.get()).ifPresent(TickCounterBlockEntity::resetCounter);
    }
    public LithicTickCounterEntity(BlockPos pos, BlockState state)
    {
        super(LithicBlockEntities.TICK_COUNTER.get(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType(){ return LithicBlockEntities.TICK_COUNTER.get();}
}
