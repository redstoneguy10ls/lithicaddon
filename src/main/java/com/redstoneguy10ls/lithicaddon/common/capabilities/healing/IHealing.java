package com.redstoneguy10ls.lithicaddon.common.capabilities.healing;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public interface IHealing extends INBTSerializable<CompoundTag> {

    UUID target();

    boolean healingSelf();

    void setTarget(UUID entry);

    void setSelf(boolean entry);

    void resetTarget();

    void remove();
}
