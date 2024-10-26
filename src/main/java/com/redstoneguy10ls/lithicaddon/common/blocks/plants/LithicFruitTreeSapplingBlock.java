package com.redstoneguy10ls.lithicaddon.common.blocks.plants;

import com.redstoneguy10ls.lithicaddon.common.blockentities.LithicTickCounterEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeSaplingBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class LithicFruitTreeSapplingBlock extends FruitTreeSaplingBlock {

    public LithicFruitTreeSapplingBlock(ExtendedProperties properties, Supplier<? extends Block> block, int treeGrowthDays, Supplier<ClimateRange> climateRange, Lifecycle[] stages)
    {
        super(properties, block, treeGrowthDays, climateRange, stages);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        LithicTickCounterEntity.reset(level,pos);
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public void createTree(Level level, BlockPos pos, BlockState state, RandomSource random) {
        final boolean onBranch = Helpers.isBlock(level.getBlockState(pos.below()), TFCTags.Blocks.FRUIT_TREE_BRANCH);
        int internalSapling = onBranch ? 3 : state.getValue(TFCBlockStateProperties.SAPLINGS);
        if(internalSapling == 1 && random.nextBoolean()) internalSapling +=1;
        level.setBlockAndUpdate(pos, block.get().defaultBlockState().setValue(PipeBlock.DOWN, true).setValue(TFCBlockStateProperties.SAPLINGS, internalSapling).setValue(TFCBlockStateProperties.STAGE_3, onBranch ? 1 : 0));
        LithicTickCounterEntity.reset(level,pos);
    }
}
