package com.redstoneguy10ls.lithicaddon.common.container;

import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class mothboxContainer extends BlockEntityContainer<mothBlockEntity> {

    public static mothboxContainer create(mothBlockEntity box, Inventory playerInventory, int windowId)
    {
        return new mothboxContainer(box, playerInventory, windowId).init(playerInventory);
    }
    public mothboxContainer(mothBlockEntity blockEntity, Inventory playerInv, int windowId)
    {
        super(LithicContainerTypes.MOTHBOX.get(), windowId, blockEntity);
    }
    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(inventory -> {
            for (int i = 0; i < mothBlockEntity.LATTICE_SLOTS; i++)
            {
                addSlot(new CallbackSlot(blockEntity, inventory, i, 44 + (i * 18), 19));
            }
            addSlot(new CallbackSlot(blockEntity, inventory, mothBlockEntity.LEAF_SLOT, 8, 35));
            addSlot(new CallbackSlot(blockEntity, inventory, mothBlockEntity.STRING_SLOT, 80, 49));
        });
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
        {
          case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, mothBlockEntity.STRING_SLOT, false);
            case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
        };

    }
}
