package com.redstoneguy10ls.lithicaddon.util;

import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.eerussianguy.firmalife.common.blocks.plant.FLFruitBlocks;
import com.eerussianguy.firmalife.common.items.FLFood;
import com.eerussianguy.firmalife.common.items.FLItems;
import com.eerussianguy.firmalife.common.util.FLFruit;
import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.blocks.plants.LithicFruitBlocks;
import com.redstoneguy10ls.lithicaddon.common.items.LithicFood;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.FruitBlocks;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public enum FruitPicker
{
    GREEN_APPLE(Food.GREEN_APPLE, TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.GREEN_APPLE).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.GREEN_APPLE).get()),
    LEMON(Food.LEMON,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.LEMON).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.LEMON).get()),
    OLIVE(Food.OLIVE,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.OLIVE).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.OLIVE).get()),
    ORANGE(Food.ORANGE,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.ORANGE).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.ORANGE).get()),
    PEACH(Food.PEACH,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.PEACH).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.PEACH).get()),
    PLUM(Food.PLUM,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.PLUM).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.PLUM).get()),
    RED_APPLE(Food.RED_APPLE,TFCBlocks.FRUIT_TREE_LEAVES.get(FruitBlocks.Tree.RED_APPLE).get(),TFCBlocks.FRUIT_TREE_BRANCHES.get(FruitBlocks.Tree.RED_APPLE).get()),
    COCOA(FLItems.FOODS.get(FLFood.COCOA_BEANS).get(), FLBlocks.FRUIT_TREE_LEAVES.get(FLFruitBlocks.Tree.COCOA).get(),FLBlocks.FRUIT_TREE_BRANCHES.get(FLFruitBlocks.Tree.COCOA).get()),
    FIG(FLItems.FRUITS.get(FLFruit.FIG).get(),FLBlocks.FRUIT_TREE_LEAVES.get(FLFruitBlocks.Tree.FIG).get(),FLBlocks.FRUIT_TREE_BRANCHES.get(FLFruitBlocks.Tree.FIG).get()),
    MULBERRY(LithicItems.FOODS.get(LithicFood.MULBERRY).get(), LithicBlocks.FRUIT_TREE_LEAVES.get(LithicFruitBlocks.Tree.MULBERRY).get(),LithicBlocks.FRUIT_TREE_BRANCHES.get(LithicFruitBlocks.Tree.MULBERRY).get())
    ;

    public final Item fruit;
    public final Block leaf;
    public final Block branch;

    FruitPicker(Food food,Block leaves, Block branch)
    {
        this(TFCItems.FOOD.get(food).get(),leaves,branch);
    }
    FruitPicker(Item item,Block leaves, Block branch)
    {
        this.fruit = item;
        this.leaf = leaves;
        this.branch = branch;
    }

    public Block getBranch() {
        return branch;
    }
    public Block getLeaf()
    {
        return leaf;
    }

    public Item getFruit() {
        return fruit;
    }
}
