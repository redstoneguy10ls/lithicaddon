package com.redstoneguy10ls.lithicaddon.common.blocks.plants;

import com.redstoneguy10ls.lithicaddon.util.LithicTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class LithicFruitLeavesBlock extends FruitTreeLeavesBlock {

    public LithicFruitLeavesBlock(ExtendedProperties properties, Supplier<? extends Item> productItem, Lifecycle[] stages, Supplier<ClimateRange> climateRange, int color)
    {
        super(properties, productItem, stages, climateRange, color);
    }


}
