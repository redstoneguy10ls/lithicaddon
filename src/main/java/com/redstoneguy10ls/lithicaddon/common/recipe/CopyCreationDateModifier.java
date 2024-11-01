package com.redstoneguy10ls.lithicaddon.common.recipe;

import com.google.common.collect.Lists;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.dries007.tfc.common.recipes.RecipeHelpers;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public enum CopyCreationDateModifier implements ItemStackModifier.SingleInstance<CopyCreationDateModifier>{

    INSTANCE;

    @Override
    public ItemStack apply(ItemStack stack, ItemStack itemStack) {

        for(ItemStack input : Lists.newArrayList(RecipeHelpers.getCraftingInput()))
        {
            IFood oldFood = FoodCapability.get(input);
            if(oldFood != null)
            {
                return FoodCapability.setCreationDate(stack,oldFood.getCreationDate());
            }
        }
        return stack;


    }
    @Override
    public boolean dependsOnInput()
    {
        return true;
    }
    @Override
    public CopyCreationDateModifier instance(){return INSTANCE;}
}
