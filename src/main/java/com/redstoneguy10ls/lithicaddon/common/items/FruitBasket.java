package com.redstoneguy10ls.lithicaddon.common.items;

import com.eerussianguy.firmalife.common.capabilities.bee.BeeAbility;
import com.eerussianguy.firmalife.common.capabilities.bee.BeeCapability;
import com.redstoneguy10ls.lithicaddon.config.LithicConfig;
import com.redstoneguy10ls.lithicaddon.util.FruitPicker;
import com.redstoneguy10ls.lithicaddon.util.LithicHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock.LIFECYCLE;
import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.FRUITING;

public class FruitBasket extends Item {

    private boolean gay;
    private final int range = Helpers.getValueOrDefault(LithicConfig.SERVER.fruitBasketSearchRadius);
    public FruitBasket(Properties pProperties) {
        super(pProperties);
        gay = false;
    }


    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess carried)
    {
        if(action == ClickAction.SECONDARY && isRegularBasket() && stack.getDamageValue() == 0)
        {
            return other.getCapability(BeeCapability.CAPABILITY).map(bee->{
                if(bee.hasQueen())
                {
                    int[] beeAbilities = bee.getAbilityMap();
                    int abilitytotal = 0;
                    List<BeeAbility> abilities = Arrays.asList(BeeAbility.VALUES);
                    for (BeeAbility ability : abilities)
                    {
                        abilitytotal += beeAbilities[ability.ordinal()];
                    }
                    if(abilitytotal > 3)
                    {
                        other.shrink(1);
                        slot.set(new ItemStack(LithicItems.FRUIT_BEESKET.get()));
                    }
                }

                return false;
            }).orElse(false);
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {

        gay = !gay;
        if(gay)
        {
            return InteractionResult.PASS;
        }


        final Level level = context.getLevel();
        final BlockPos pos = context.getClickedPos();
        final BlockState state = level.getBlockState(pos);
        final Player player = context.getPlayer();
        final ItemStack held = context.getItemInHand();
        final int tree = LithicHelpers.getTreeType(state);

        //if it's a fruit tree branch
        if( (Helpers.isBlock(state, TFCTags.Blocks.FRUIT_TREE_BRANCH) ) && state.canSurvive(level,pos) && tree > -1)
        {
            //if( (!Helpers.isBlock(level.getBlockState(pos.below()),TFCTags.Blocks.FRUIT_TREE_LEAVES) || !Helpers.isBlock(level.getBlockState(pos.below()),TFCTags.Blocks.FRUIT_TREE_BRANCH) )
            //        && state.getValue(PipeBlock.UP))
            int fruitingLeaves = 0;
            final BlockPos min = pos.offset(range*-1, 0, range*-1);
            final BlockPos max = pos.offset(1, 7, 1);
            if(level.hasChunksAt(min, max))
            {
                for (BlockPos pos1 : BlockPos.betweenClosed(min, max))
                {
                    final BlockState state1 = level.getBlockState(pos1);

                    if (Helpers.isBlock(state1, FruitPicker.values()[tree].getLeaf() ))
                    {
                        if (state1.getValue(LIFECYCLE) == FRUITING)
                        {
                            fruitingLeaves++;
                            level.setBlockAndUpdate(pos1, state1.setValue(LIFECYCLE, Lifecycle.HEALTHY));
                        }
                    }
                }
                
                if (isRegularBasket() && fruitingLeaves > 0)
                {
                    if (level.random.nextDouble() <= Helpers.getValueOrDefault(LithicConfig.SERVER.fruitTreeBranchBreakChance))
                    {
                        level.destroyBlock(pos, true);
                    }
                }
                held.hurtAndBreak(fruitingLeaves, player, p -> {});
                while (fruitingLeaves > 0)
                {
                    player.getInventory().add(new ItemStack(FruitPicker.values()[tree].getFruit(), 1));
                    player.playSound(SoundEvents.ITEM_PICKUP,0.1f,1);
                    fruitingLeaves--;
                }


            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    public boolean isRegularBasket()
    {
      return true;
    }
}
