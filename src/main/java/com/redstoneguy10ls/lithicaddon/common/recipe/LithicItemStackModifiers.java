package com.redstoneguy10ls.lithicaddon.common.recipe;


import com.redstoneguy10ls.lithicaddon.util.LithicHelpers;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifiers;

public class LithicItemStackModifiers {

    public static void init()
    {
        register("copy_creation_date", CopyCreationDateModifier.INSTANCE);
    }
    private static void register(String name, ItemStackModifier.Serializer<?> serializer)
    {
        ItemStackModifiers.register(LithicHelpers.identifier(name),serializer);
    }
}
