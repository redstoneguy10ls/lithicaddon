package com.redstoneguy10ls.lithicaddon.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public enum LithicFood {
    BOILED_GRUB(true,true,false),
    MULBERRY(false,false,true),
    SOAKED_SOY,
    GROUND_SOY,
    FISH_FILLET(true,false,false),
    COOKED_FISH_FILLET(true,false,false),
    SOY,
    SOY_CURD;

    private final boolean meat, fast,fruit;

    LithicFood(){this(false,false,false);}

    LithicFood(boolean meat, boolean fast, boolean fruit)
    {
        this.meat = meat;
        this.fast = fast;
        this.fruit =fruit;
    }
    public boolean isFruit()
    {
        return fruit;
    }

    public FoodProperties getFoodProperties()
    {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if(meat) builder.meat();
        if(fast) builder.fast();
        return builder.nutrition(4).saturationMod(0.3f).build();
    }
    public Item.Properties createProperties()
    {
        return new Item.Properties().food(getFoodProperties());
    }
}
