package com.redstoneguy10ls.lithicaddon.util;


import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.FruitBlocks;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;


public class LithicHelpers {
    public static ResourceLocation identifier(String id)
    {
        return LithicHelpers.res(MOD_ID, id);
    }

    public static ResourceLocation res(String namespace, String path)
    {
        return new ResourceLocation(namespace, path);
    }


    public static int getTreeType(BlockState state)
    {
        for(FruitPicker gays : FruitPicker.values())
        {
            if(Helpers.isBlock(state,gays.getBranch()))
            {
                return gays.ordinal();
            }
        }

        /*
        for(FruitBlocks.Tree tree : FruitBlocks.Tree.values())
        {
            if(Helpers.isBlock(state,TFCBlocks.FRUIT_TREE_BRANCHES.get(tree).get()))
            {
                return tree;
            }
        }

         */
        return -1;
    }

    public static Food getFruit(FruitBlocks.Tree tree)
    {
        return switch (tree.ordinal())
        {
            case 0 -> Food.CHERRY;
            case 1 -> Food.GREEN_APPLE;
            case 2 -> Food.LEMON;
            case 3 -> Food.OLIVE;
            case 4 -> Food.ORANGE;
            case 5 -> Food.PEACH;
            case 6 -> Food.PLUM;
            case 7 -> Food.RED_APPLE;
            default -> throw new IllegalStateException("Unexpected value: " + tree.ordinal());
        };
    }
}
