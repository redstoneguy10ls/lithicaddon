package com.redstoneguy10ls.lithicaddon;

import com.eerussianguy.firmalife.common.blockentities.VatBlockEntity;
import com.eerussianguy.firmalife.common.blocks.VatBlock;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import com.redstoneguy10ls.lithicaddon.util.advancements.LithicAdvancements;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.forge.ForgingBonus;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        BlockEntity block = event.getLevel().getBlockEntity(event.getPos());
        Player player = event.getEntity();
        if(block instanceof VatBlockEntity vat)
        {
            final ItemStack stack = player.getItemInHand(event.getHand());
            if(!vat.isBoiling())
            {
                if(vat.hasOutput())
                {

                    if(stack.getItem() == TFCItems.EMPTY_JAR_WITH_LID.get())
                    {
                        event.setCanceled(true);
                    }


                    if(Helpers.isItem(stack, TFCTags.Items.EMPTY_JAR_WITH_LID))
                    {
                        stack.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(player,vat.takeOutput());
                        event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide));
                        event.setCanceled(true);
                    }
                }
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
