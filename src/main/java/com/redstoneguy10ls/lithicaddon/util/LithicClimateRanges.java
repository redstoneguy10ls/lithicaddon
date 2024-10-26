package com.redstoneguy10ls.lithicaddon.util;

import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.blocks.plants.LithicFruitBlocks;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.RegisteredDataManager;
import net.dries007.tfc.util.climate.ClimateRange;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class LithicClimateRanges {
    //should have made this before working on the moths

    public static final Map<LithicFruitBlocks.Tree, Supplier<ClimateRange>> FRUIT_TREES =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> register("plant/" +tree.name() + "_tree"));

    private static RegisteredDataManager.Entry<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.register(LithicHelpers.identifier(name.toLowerCase(Locale.ROOT)));
    }
}
