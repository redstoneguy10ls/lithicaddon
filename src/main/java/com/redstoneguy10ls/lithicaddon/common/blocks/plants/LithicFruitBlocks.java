package com.redstoneguy10ls.lithicaddon.common.blocks.plants;

import com.redstoneguy10ls.lithicaddon.common.blockentities.LithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.items.LithicFood;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import com.redstoneguy10ls.lithicaddon.util.LithicClimateRanges;
import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeBranchBlock;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.awt.*;
import java.util.function.Supplier;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public final class LithicFruitBlocks
{
    public enum Tree
    {
        MULBERRY(LithicItems.FOODS.get(LithicFood.MULBERRY), new Color(62,8,43).getRGB(), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT});

        private final Supplier<Item> product;
        private final int color;
        private final Lifecycle[] stages;

        Tree(Supplier<Item> product, int color, Lifecycle[] stages)
        {
            this.product = product;
            this.color = color;
            this.stages = stages;
        }
        public Block createSapling()
        {
            return new LithicFruitTreeSapplingBlock(ExtendedProperties.of()
                    .noCollission()
                    .randomTicks()
                    .strength(0)
                    .sound(SoundType.GRASS)
                    .blockEntity(LithicBlockEntities.TICK_COUNTER)
                    .flammableLikeLeaves(),LithicBlocks.FRUIT_TREE_GROWING_BRANCHES.get(this), 8, LithicClimateRanges.FRUIT_TREES.get(this), stages);
        }
        public Block createPottedSapling()
        {
            return new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LithicBlocks.FRUIT_TREE_SAPLINGS.get(this),
                    BlockBehaviour.Properties.of().instabreak().noOcclusion());
        }

        public Block createLeaves()
        {
            return new LithicFruitLeavesBlock(ExtendedProperties.of()
                    .mapColor(FruitTreeLeavesBlock::getMapColor)
                    .strength(0.5F).sound(SoundType.GRASS)
                    .randomTicks().noOcclusion()
                    .blockEntity(LithicBlockEntities.BERRY_BUSH)
                    .serverTicks(BerryBushBlockEntity::serverTick)
                    .flammableLikeLeaves(), product,stages, LithicClimateRanges.FRUIT_TREES.get(this), color);
        }
        public Block createBranch()
        {
            return new FruitTreeBranchBlock(ExtendedProperties.of()
                    .mapColor(MapColor.WOOD).sound(SoundType.SCAFFOLDING)
                    .randomTicks().strength(1.0f).flammableLikeLogs(),LithicClimateRanges.FRUIT_TREES.get(this));
        }
        public Block createGrowingBranch()
        {
            return new LithicGrowingFruitTreeBranchBlock(
                ExtendedProperties.of()
                        .mapColor(MapColor.WOOD)
                        .sound(SoundType.SCAFFOLDING)
                        .randomTicks()
                        .strength(1.0f)
                        .blockEntity(LithicBlockEntities.TICK_COUNTER)
                        .flammableLikeLogs(),LithicBlocks.FRUIT_TREE_BRANCHES.get(this), LithicBlocks.FRUIT_TREE_LEAVES.get(this), LithicClimateRanges.FRUIT_TREES.get(this)
            );
        }

    }

}
