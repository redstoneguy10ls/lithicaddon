package com.redstoneguy10ls.lithicaddon.common.capabilities.healing;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HealingHandler implements IHealing, ICapabilitySerializable<CompoundTag> {

    private final LazyOptional<IHealing> capability;
    private final ItemStack stack;

    private UUID target;

    private boolean healingself;

    private boolean initialized = false;

    public HealingHandler(ItemStack stack) {
        this.capability = LazyOptional.of(() -> this);
        this.stack = stack;
        this.target = null;
        this.healingself = false;
    }


    @Override
    public UUID target() {
        return target;
    }

    @Override
    public void setSelf(boolean entry) {
        healingself = entry;
        save();
    }

    @Override
    public boolean healingSelf() {
        return healingself;
    }

    @Override
    public void resetTarget() {
        healingself = false;
        save();
    }

    @Override
    public void setTarget(UUID entry) {
        target = entry;
        save();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == HealingCapability.CAPABILITY)
        {
            load();
            return capability.cast();
        }
        return LazyOptional.empty();
    }

    private void load()
    {
        if(!initialized)
        {
            initialized = true;

            final CompoundTag tag = stack.getOrCreateTag();
            if(tag.contains("target"))
            {
                target = tag.getUUID("target");
            }
        }
    }

    private void save()
    {
        final CompoundTag tag = stack.getOrCreateTag();
            tag.putUUID("target", target);

    }

    public void remove()
    {
        final CompoundTag tag = stack.getOrCreateTag();
        tag.remove("target");
        target = null;
    }


    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
