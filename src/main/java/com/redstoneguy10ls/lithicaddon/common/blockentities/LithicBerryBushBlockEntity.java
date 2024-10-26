package com.redstoneguy10ls.lithicaddon.common.blockentities;

import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LithicBerryBushBlockEntity extends BerryBushBlockEntity {

    public LithicBerryBushBlockEntity(BlockPos pos, BlockState state){super(pos, state);}

    protected LithicBerryBushBlockEntity(BlockEntityType<?> type,BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return LithicBlockEntities.BERRY_BUSH.get();
    }
}
