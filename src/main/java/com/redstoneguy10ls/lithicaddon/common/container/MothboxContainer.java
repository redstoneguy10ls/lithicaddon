package com.redstoneguy10ls.lithicaddon.common.container;

import com.redstoneguy10ls.lithicaddon.common.blockentities.MothBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class MothboxContainer extends BlockEntityContainer<MothBlockEntity> {

    public static MothboxContainer create(MothBlockEntity box, Inventory playerInventory, int windowId)
    {
        return new MothboxContainer(box, playerInventory, windowId).init(playerInventory);
    }
    public MothboxContainer(MothBlockEntity blockEntity, Inventory playerInv, int windowId)
    {
        super(LithicContainerTypes.MOTHBOX.get(), windowId, blockEntity);
    }
    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(inventory -> {
            for (int i = 0; i < MothBlockEntity.LATTICE_SLOTS; i++)
            {
                addSlot(new CallbackSlot(blockEntity, inventory, i, 44 + (i * 18), 19));
            }
            addSlot(new CallbackSlot(blockEntity, inventory, MothBlockEntity.LEAF_SLOT, 8, 35));
            addSlot(new CallbackSlot(blockEntity, inventory, MothBlockEntity.STRING_SLOT, 80, 49));
        });
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
        {
          case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, MothBlockEntity.STRING_SLOT, false);
            case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
        };

    }
}
