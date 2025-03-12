package com.redstoneguy10ls.lithicaddon.config;


import net.minecraftforge.common.ForgeConfigSpec.*;

import java.util.function.Function;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class LithicServerConfig {

    public final IntValue daysTillCocoon;
    public final IntValue daysTillMoth;


    public final IntValue minLightLevel;

    public final IntValue minLights;

    public final DoubleValue mintemp;

    public final DoubleValue maxtemp;

    public final DoubleValue minrain;
    public final DoubleValue maxrain;

    public final IntValue mothEatChance;

    public final BooleanValue betterHorseBreeding;

    public final IntValue fruitTreeBreakChance;



    LithicServerConfig(Builder innerBuilder)
    {
        Function<String, Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.server." + name);

        innerBuilder.push("general");

        daysTillCocoon = builder.apply("daysTillCocoon")
                .comment("The number of days a silk worm needs to be fed for it to become a cocoon")
                .defineInRange("daysTillCocoon",22,1,Integer.MAX_VALUE);
        daysTillMoth = builder.apply("daysTillMoth")
                .comment("The number of days a silk worm needs to be in a cocoon for it to become a moth")
                .defineInRange("daysTillMoth",11,1,Integer.MAX_VALUE);

        minLightLevel = builder.apply("minLightLevel")
                .comment("The minimum light level a block needs to be to be counted by the mothbox")
                .defineInRange("minLightLevel",13,1,15);
        minLights = builder.apply("minLights")
                .comment("The minimum amount of blocks with light level minLightLevel or grater to have the mothbox start working")
                .defineInRange("minLights",10,1,Integer.MAX_VALUE);


        mintemp = builder.apply("mintemp")
                .comment("The minimum tempature for the mothbox to start working")
                .defineInRange("mintemp",19.0f, -50.0f, 50.0f);

        maxtemp = builder.apply("maxtemp")
                .comment("The maximum tempature for the mothbox to start working")
                .defineInRange("maxtemp",29.0f, -50.0f, 50.0f);

        minrain = builder.apply("minrain")
                .comment("The minimum rainfall for the mothbox to start working")
                .defineInRange("minrain",300.0f, 0.0f, 500.0f);

        maxrain = builder.apply("maxrain")
                .comment("The maximum rainfall for the mothbox to start working")
                .defineInRange("maxrain",400.0f, 0.0f, 500.0f);

        mothEatChance = builder.apply("mothEatChance")
                .comment("The chance out of 100 the fruit leaves will be consumed each day. set to 100 to gaurentee they eat ")
                .defineInRange("mothEatChance",50, 0, 100);

        betterHorseBreeding = builder.apply("betterHorseBreeding")
                        .comment("Whether or not make horse offsprings get a bonus to speed based on the parents familiarity")
                        .define("betterHorseBreeding", true);

        fruitTreeBreakChance = builder.apply("fruitTreeBreakChance")
                .comment("The chance out of 100 that a fruit tree breaks when using a fruit basket. Set to zero to disable")
                .defineInRange("fruitTreeBreakChance",5, 0, 100);


        innerBuilder.pop();
    }

}
