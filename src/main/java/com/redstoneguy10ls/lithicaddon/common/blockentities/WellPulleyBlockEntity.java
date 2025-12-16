package com.redstoneguy10ls.lithicaddon.common.blockentities;

import com.redstoneguy10ls.lithicaddon.common.container.WellPulleyContainer;
import com.redstoneguy10ls.lithicaddon.util.LithicTags;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.DelegateItemHandler;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class WellPulleyBlockEntity extends InventoryBlockEntity<ItemStackHandler>
{
    public static final int MAX_CHAIN_SLOTS = 2;
    public int chainDepth = 0;
    
    private static final Component NAME = Component.translatable("lithicaddon.block_entity.wellpulley");
    
    public WellPulleyBlockEntity(BlockPos pos, BlockState state)
    {
      super(LithicBlockEntities.WELL_PULLEY.get(), pos, state, defaultInventory(3), NAME);
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player)
    {
      return WellPulleyContainer.create(this, player.getInventory(), containerId);
    }
    
    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return Helpers.isItem(stack, LithicTags.Items.CHAINS);
    }
    
    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("chain_depth", this.chainDepth);
    }
    
    @Override
    public void loadAdditional(CompoundTag nbt) {
        super.loadAdditional(nbt);
        this.chainDepth = nbt.getInt("chain_depth");
    }
    
    public void dropBucket()
    {
        int chains = 0;
        for(int i = 0; i < MAX_CHAIN_SLOTS; i++)
        {
            chains += inventory.getStackInSlot(i).getCount();
        }
        if(chains > 0)
        {
        
        }
    }

}
