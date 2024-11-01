package com.redstoneguy10ls.lithicaddon.util.advancements;


import com.redstoneguy10ls.lithicaddon.util.LithicHelpers;
import net.minecraft.advancements.CriteriaTriggers;

public class LithicAdvancements
{
    public static void registerTriggers() { }

    public static final GenericTrigger REALLY_SERIOUS_DEDICATION = registerGeneric("really_serious_dedication");

    public static GenericTrigger registerGeneric(String name)
    {
        return CriteriaTriggers.register(new GenericTrigger(LithicHelpers.identifier(name)));
    }

}
