package com.redstoneguy10ls.lithicaddon.worldgen;


import com.mojang.serialization.Codec;
import com.redstoneguy10ls.lithicaddon.common.blockentities.LithicBlockEntities;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.plant.fruit.GrowingFruitTreeBranchBlock;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class LithicFruitTreeFeature extends Feature<BlockStateConfiguration> {

    public LithicFruitTreeFeature(Codec<BlockStateConfiguration> codec){super(codec);}
    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();
        final RandomSource rand = context.random();
        final BlockStateConfiguration config = context.config();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        mutablePos.set(pos).move(0, -1, 0);

        if (Helpers.isBlock(level.getBlockState(mutablePos), TFCTags.Blocks.BUSH_PLANTABLE_ON))
        {
            mutablePos.move(0, 1, 0);
            for (int j = 1; j <= 10; j++)
            {
                if (!EnvironmentHelpers.isWorldgenReplaceable(level, mutablePos))
                {
                    return false;
                }
                mutablePos.move(0, 1, 0);
            }
            mutablePos.set(pos);
            int saplings = Mth.nextInt(rand, 2, 4);
            BlockState branch = config.state.getBlock().defaultBlockState().setValue(GrowingFruitTreeBranchBlock.SAPLINGS, saplings);
            setBlock(level, mutablePos, branch);
            level.getBlockEntity(mutablePos, LithicBlockEntities.TICK_COUNTER.get()).ifPresent(entity -> entity.reduceCounter(-1 * ICalendar.TICKS_IN_DAY * 300));
            level.scheduleTick(mutablePos, branch.getBlock(), 1);
            return true;
        }
        return false;
    }
}
