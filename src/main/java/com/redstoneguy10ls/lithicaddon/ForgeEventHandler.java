package com.redstoneguy10ls.lithicaddon;

import com.eerussianguy.firmalife.common.FLForgeEvents;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import net.dries007.tfc.common.capabilities.forge.ForgingBonus;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ForgeEventHandler {

    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(ForgeEventHandler::PlayerDestroyItemEvent);
    }


    public static void PlayerDestroyItemEvent(PlayerDestroyItemEvent event)
    {
        if(Helpers.isItem(event.getOriginal(), LithicItems.METAL_SPINDLES.get(Metal.Default.RED_STEEL).get())
                || Helpers.isItem(event.getOriginal(), LithicItems.METAL_SPINDLES.get(Metal.Default.BLUE_STEEL).get()))
        {
            final ForgingBonus bonus = ForgingBonus.get(event.getOriginal());
            if(bonus == ForgingBonus.PERFECTLY_FORGED)
            {
                //todo add advancement
            }
        }
    }
}
