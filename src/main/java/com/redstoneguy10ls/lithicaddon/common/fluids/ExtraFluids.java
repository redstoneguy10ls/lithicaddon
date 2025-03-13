package com.redstoneguy10ls.lithicaddon.common.fluids;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ExtraFluids implements StringRepresentable {
    UNFILTERED_SOYMILK(0xFFBDBCB1),
    SOYMILK(0xFFFCFBED),
    CURDLED_SOY_MILK(0xFFAFA599);
    private final String id;
    private final int color;

    ExtraFluids(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }
    @Override
    public String getSerializedName()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }
}
