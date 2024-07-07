package com.redstoneguy10ls.lithicaddon.common.capabilities.healing;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class HealingCapability {

    public static final Capability<IHealing> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation KEY = new ResourceLocation("lithicaddon","healing");
}
