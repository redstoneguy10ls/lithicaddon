package com.redstoneguy10ls.lithicaddon.common.capabilities.moth;

import com.redstoneguy10ls.lithicaddon.config.LithicConfig;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface IMoth extends INBTSerializable<CompoundTag> {

    int DAYS_TILL_COCOON = Helpers.getValueOrDefault(LithicConfig.SERVER.daysTillCocoon);
    int DAYS_TILL_MOTH = Helpers.getValueOrDefault(LithicConfig.SERVER.daysTillMoth)+DAYS_TILL_COCOON;


    int daysAlive();

    default int getDaysTillCocoon(){return DAYS_TILL_COCOON;}
    default int getDaysTillMoth(){return DAYS_TILL_MOTH;}

    void setDaysAlive(int value);

    void setHasLarva(boolean exists);

    boolean hasLarva();

    boolean hasCocoon();
    boolean isMoth();

    void setHasCocoon(boolean exists);
    void setIsMoth(boolean exists);

    void kill();

    default void initLarva()
    {
        setDaysAlive(1);
        setHasLarva(true);
    }

    int[] getAbilityMap();
    void setAbilities(int[] abilities);

    void setAbility(MothAbility ability, int value);

    default int getAbility(MothAbility ability){return getAbilityMap()[ability.ordinal()];}

    default void initFreshAbilities(RandomSource random)
    {
        final int[] values = MothAbility.fresh();
        values[random.nextInt(values.length)] = random.nextInt(3)+1;
        if (random.nextFloat() < 0.1f)
        {
            values[random.nextInt(values.length)] = random.nextInt(3) + 1;
        }
        setAbilities(values);
        initLarva();
    }
    default void setAbilitiesFromParents(IMoth parent1, IMoth parent2, RandomSource random)
    {
        int[] parent1Abilities = parent1.getAbilityMap();
        int[] parent2Abilities = parent2.getAbilityMap();

        int abilitiesSet = 0;
        List<MothAbility> abilities = Arrays.asList(MothAbility.VALUES);
        Collections.shuffle(abilities);

        int min_bonus = random.nextInt(1);
        int max_bonus = random.nextInt(3)+1+min_bonus;

        for(MothAbility ability : abilities)
        {
            int average = (parent1Abilities[ability.ordinal()] + parent2Abilities[ability.ordinal()]) /2;
            if(average >= 1 && abilitiesSet < 3)
            {
                abilitiesSet++;
                setAbility(ability, Mth.nextInt(random, average, average+max_bonus));
            }
        }
        if(abilitiesSet == 0)
        {
            initFreshAbilities(random);
        }
        else
        {
            initLarva();
        }


    }


    default void addToolTipInfo(List<Component> tooltip)
    {
        if(hasLarva())
        {

            if(isMoth())
            {
                tooltip.add(Component.translatable("lithic.moth.moth").withStyle(ChatFormatting.GOLD));
            } else if (hasCocoon())
            {
                tooltip.add(Component.translatable("lithic.moth.cocoon").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("lithic.moth.till_moth", String.valueOf(DAYS_TILL_MOTH+MothAbility.getTimeBonus(getAbility(MothAbility.FASTING),getAbility(MothAbility.HUNGER)) - daysAlive()+1)).withStyle(ChatFormatting.WHITE));
            }
            else {
                tooltip.add(Component.translatable("lithic.moth.larva").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("lithic.moth.till_cocoon", String.valueOf(DAYS_TILL_COCOON+MothAbility.getTimeBonus(getAbility(MothAbility.FASTING),getAbility(MothAbility.HUNGER)) -daysAlive()+1) ).withStyle(ChatFormatting.WHITE));
            }
            for (MothAbility ability : MothAbility.VALUES)
            {
                final int amount = getAbility(ability);
                if (amount > 0)
                {
                    tooltip.add(Component.translatable("lithic.moth.ability." + ability.getSerializedName(), String.valueOf(amount)).withStyle(ChatFormatting.GRAY));
                }
            }
        }
        else
        {
            tooltip.add(Component.translatable("lithic.moth.no_larva").withStyle(ChatFormatting.RED));
        }
    }

    //void addTooltipInfo(ItemStack stack, List<Component> tooltip);
}
