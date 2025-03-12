package com.redstoneguy10ls.lithicaddon.common.items;

import com.eerussianguy.firmalife.common.capabilities.bee.BeeCapability;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.config.LithicConfig;
import com.redstoneguy10ls.lithicaddon.util.FruitPicker;
import com.redstoneguy10ls.lithicaddon.util.LithicHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import static net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock.LIFECYCLE;
import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.FRUITING;

public class FruitBEEsket extends FruitBasket {
    public FruitBEEsket(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean beesket()
    {
      return true;
    }
}
