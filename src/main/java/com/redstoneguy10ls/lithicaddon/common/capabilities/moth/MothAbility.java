package com.redstoneguy10ls.lithicaddon.common.capabilities.moth;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Locale;

public enum MothAbility implements StringRepresentable {
    MOTH_SIZE,
    FERTILITY,
    HARDINESS,
    HUNGER,
    FASTING;

    public static float getMinTemperature(int hardiness)
    {
        return -1 * hardiness;
    }
    public static float getMaxTemperature(int hardiness)
    {
        return hardiness;
    }
    public static float getMinRainfall(int hardiness)
    {
        return -5 * hardiness;
    }
    public static float getMaxRainfall(int hardiness)
    {
        return 5 * hardiness;
    }

    public static int getTimeBonus(int fasting,int hunger)
    {
        return fasting-hunger;
    }



    public static int[] fresh()
    {
        final int[] ints = new int[MothAbility.SIZE];
        Arrays.fill(ints,0);
        return ints;
    }
    public static final MothAbility[] VALUES = values();

    public static final int SIZE = values().length;

    private final String name;

    MothAbility(){this.name = name().toLowerCase(Locale.ROOT);}

    @Override
    public String getSerializedName() {
        return name;
    }
}
