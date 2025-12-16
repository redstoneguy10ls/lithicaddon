package com.redstoneguy10ls.lithicaddon.common.container;

import com.redstoneguy10ls.lithicaddon.common.blockentities.WellPulleyBlockEntity;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WellPulleyContainer extends BlockEntityContainer<WellPulleyBlockEntity> implements ButtonHandlerContainer
{
    public static WellPulleyContainer create(WellPulleyBlockEntity well, Inventory playerInv, int windowId)
    {
        return new WellPulleyContainer(windowId, well).init(playerInv, 12);
    }
    
    private WellPulleyContainer(int windowId, WellPulleyBlockEntity well)
    {
        super(LithicContainerTypes.WELL_PULLEY.get(), windowId, well);
    }
  
    
    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
    }
    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(inventory -> {
          for (int slot = 0; slot <= WellPulleyBlockEntity.MAX_CHAIN_SLOTS; slot++)
          {
            addSlot(new CallbackSlot(blockEntity, inventory, slot, 44 + (slot * 18), 19));
          }
        });
    }
  
  @Override
  protected boolean moveStack(ItemStack stack, int slotIndex)
  {
      return super.moveStack(stack, slotIndex);
  }
}
