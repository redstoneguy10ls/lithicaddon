package com.redstoneguy10ls.lithicaddon.util;


import net.minecraft.resources.ResourceLocation;

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
}
