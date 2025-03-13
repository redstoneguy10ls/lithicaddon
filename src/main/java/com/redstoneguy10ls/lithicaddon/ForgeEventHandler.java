package com.redstoneguy10ls.lithicaddon;

import com.eerussianguy.firmalife.common.blockentities.VatBlockEntity;
import com.eerussianguy.firmalife.common.blocks.VatBlock;
import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import com.redstoneguy10ls.lithicaddon.util.advancements.LithicAdvancements;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.forge.ForgingBonus;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.ItemHandlerHelper;

public class ForgeEventHandler {

    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(ForgeEventHandler::PlayerItemCraftedEvent);
        bus.addListener(ForgeEventHandler::PlayerInteractWithBlockEvent);
        //bus.addListener(ForgeEventHandler::onPlayerInventoryTick);
    }
/*
    public static void onPlayerInventoryTick(PlayerEvent event)
    {
        Inventory inv = event.getEntity().getInventory();
        for(ItemStack stack : inv.items)
        {
            stack.getCapability(MothCapability.CAPABILITY).ifPresent(moth->{
                if(moth.isMoth())
                {
                    moth.kill();
                }
            });
        }
    }

 */

    public static void PlayerInteractWithBlockEvent(PlayerInteractEvent.RightClickBlock event)
    {
        BlockState block = event.getLevel().getBlockState(event.getPos());
        Player player = event.getEntity();

        if(Helpers.isBlock(block, Blocks.LIGHT_BLUE_WOOL))
        {
            final ItemStack stack1 = player.getItemInHand(InteractionHand.MAIN_HAND);
            final ItemStack stack2 = player.getItemInHand(InteractionHand.OFF_HAND);
            if(Helpers.isItem(stack1, Items.WHITE_DYE) && Helpers.isItem(stack2,Items.PINK_DYE))
            {
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                event.getLevel().setBlockAndUpdate(event.getPos(),LithicBlocks.SHH.get().defaultBlockState());
            }

        }
    }


    public static void PlayerItemCraftedEvent(PlayerEvent.ItemCraftedEvent event)
    {
        for(int i = 0; i < event.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = event.getInventory().getItem(i);

            if(Helpers.isItem(stack,LithicItems.METAL_SPINDLES.get(Metal.Default.RED_STEEL).get())
                || Helpers.isItem(stack,LithicItems.METAL_SPINDLES.get(Metal.Default.BLUE_STEEL).get()))
            {
                if(!Helpers.isItem(event.getCrafting(),LithicItems.METAL_SPINDLES.get(Metal.Default.RED_STEEL).get())
                        && !Helpers.isItem(event.getCrafting(),LithicItems.METAL_SPINDLES.get(Metal.Default.BLUE_STEEL).get()))
                {
                    if(stack.getDamageValue() >= stack.getMaxDamage()-1)
                    {
                        final ForgingBonus bonus = ForgingBonus.get(stack);
                        if(bonus == ForgingBonus.PERFECTLY_FORGED)
                        {
                            if(event.getEntity() instanceof ServerPlayer player)
                            {
                                event.getInventory().getItem(i).shrink(1);
                                LithicAdvancements.REALLY_SERIOUS_DEDICATION.trigger(player);
                                //System.out.println("true");
                            }
                        }
                    }
                }
            }
        }

        /*
        if(Helpers.isItem(event.getOriginal(), LithicItems.METAL_SPINDLES.get(Metal.Default.RED_STEEL).get())
                || Helpers.isItem(event.getOriginal(), LithicItems.METAL_SPINDLES.get(Metal.Default.BLUE_STEEL).get()))
        {
            final ForgingBonus bonus = ForgingBonus.get(event.getOriginal());
            if(bonus == ForgingBonus.PERFECTLY_FORGED)
            {
                if(event.getEntity() instanceof ServerPlayer player)
                {
                    LithicAdvancements.REALLY_SERIOUS_DEDICATION.trigger(player);

                }
            }
        }

         */
    }
}
