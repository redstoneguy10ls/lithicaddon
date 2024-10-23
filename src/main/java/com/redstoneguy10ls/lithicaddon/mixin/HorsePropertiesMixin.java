package com.redstoneguy10ls.lithicaddon.mixin;

import com.redstoneguy10ls.lithicaddon.config.lithicConfig;
import net.dries007.tfc.common.entities.livestock.CommonAnimalData;
import net.dries007.tfc.common.entities.livestock.MammalProperties;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.entities.livestock.horse.HorseProperties;
import net.dries007.tfc.common.entities.livestock.horse.TFCHorse;
import net.dries007.tfc.util.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TFCHorse.class)
public abstract class HorsePropertiesMixin implements HorseProperties{


    @Shadow public abstract CommonAnimalData animalData();

    @Inject(method = "createGenes", at = @At("TAIL"),remap = false)
    private void morespeed(CompoundTag tag, TFCAnimalProperties maleProperties, CallbackInfo ci)
    {
        if(Helpers.getValueOrDefault(lithicConfig.SERVER.betterHorseBreeding))
        {
            //MammalProperties.super.createGenes(tag, maleProperties);
            AbstractHorse female = getEntity();
            AbstractHorse male = (AbstractHorse) maleProperties;

            double bonus = tag.getDouble("movementSpeed2") + ((female.getEntityData().get(animalData().familiarity())-0.35) / 15);

            tag.putDouble("movementSpeed2", bonus);
            tag.putDouble("movementSpeed1", male.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)+((maleProperties.getFamiliarity()-0.35) /15));
            //tag.putDouble("movementSpeed2", female.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)+female.getEntityData().get(animalData().familiarity()));
        }

    }
/*
    public AbstractHorse getEntity()
    {
        return (AbstractHorse) MammalProperties.super.getEntity();
    }

 */

}
