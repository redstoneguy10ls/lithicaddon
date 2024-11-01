package com.redstoneguy10ls.lithicaddon.common.items;

import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothAbility;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothHandler;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.items.FluidContainerItem;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LarvaLatticeItem extends Item {

    public LarvaLatticeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if(action == ClickAction.SECONDARY)
        {
            return stack.getCapability(MothCapability.CAPABILITY).map(moth->{
                if( (moth.hasLarva() && !moth.hasCocoon()) || moth.isMoth() )
                {
                    if(Helpers.isItem(other, TFCItems.WOODEN_BUCKET.get()))
                    {
                        final IFluidHandler handler = other.getCapability(Capabilities.FLUID_ITEM).resolve().orElse(null);
                        if(handler == null)
                        {
                            return false;
                        }
                        if(Helpers.isFluid(handler.getFluidInTank(0).getFluid(), TFCTags.Fluids.ANY_WATER))
                        {
                            handler.drain(1000,IFluidHandler.FluidAction.EXECUTE);
                            stack.shrink(1);
                            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(this));
                            player.playSound(SoundEvents.PLAYER_SPLASH_HIGH_SPEED,0.5f,1f);
                            return true;
                        }

                    }
                }
                return false;
            }).orElse(false);
        }
        return false;

    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess carried)
    {
        if (action == ClickAction.SECONDARY && other.isEmpty())
        {

            AtomicInteger rands = new AtomicInteger();
            //AtomicInteger rands = new AtomicInteger(player.getRandom().nextIntBetweenInclusive(1, 8));
            return stack.getCapability(MothCapability.CAPABILITY).map(moth ->{
                if(moth.hasCocoon() && !moth.isMoth())
                {
                    rands.set(Mth.nextInt(player.getRandom(), 1 + moth.getAbility(MothAbility.MOTH_SIZE), 6 + moth.getAbility(MothAbility.MOTH_SIZE)));
                    slot.set(new ItemStack(this));
                    while(rands.get() >= 0)
                    {
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(LithicItems.COCOON.get()));
                        rands.getAndDecrement();
                    }
                    return true;
                }
                return false;
            }).orElse(false);
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced)
    {
        stack.getCapability(MothCapability.CAPABILITY).ifPresent(moth -> {
            if (moth.hasCocoon() && !moth.isMoth())
            {
                tooltip.add(Component.translatable("lithic.moth.pull").withStyle(ChatFormatting.WHITE));
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new MothHandler(stack);
    }
}
