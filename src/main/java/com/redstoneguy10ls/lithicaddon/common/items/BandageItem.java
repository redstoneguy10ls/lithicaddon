package com.redstoneguy10ls.lithicaddon.common.items;

import com.redstoneguy10ls.lithicaddon.common.capabilities.healing.HealingCapability;
import com.redstoneguy10ls.lithicaddon.common.capabilities.healing.HealingHandler;
import com.redstoneguy10ls.lithicaddon.util.LithicTags;
import net.dries007.tfc.util.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class BandageItem extends Item {

    public BandageItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.BRUSH;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        AtomicBoolean hastarget = new AtomicBoolean(false);
        ItemStack stack = player.getItemInHand(hand);
        stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> {
            if(healing.target() != null)
            {
                hastarget.set(true);
            }
            /*
            if (healing.target() == null) {
                healing.setTarget(player.getUUID());
                healing.setSelf(true);
            }
            */

        });
        if(player.getHealth() == player.getMaxHealth() && !hastarget.get())// || player.isSecondaryUseActive()
        {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (Helpers.isEntity(target, LithicTags.Entities.BANDAGEABLE_MOBS))
        {
            if(!player.getCooldowns().isOnCooldown(stack.getItem()))
            {
                if (target.getHealth() < target.getMaxHealth()) {
                    stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> {

                            healing.setTarget(target.getUUID());
                            //player.startUsingItem(hand);
                    });
                }


            }

        }

        return super.interactLivingEntity(stack,player,target,hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int pRemainingUseDuration) {



        stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> {
            if(healing.target() == null)
            {
                super.onUseTick(level, entity, stack, pRemainingUseDuration);
            }
            else
            {
                if(entity.getUseItemRemainingTicks() <= (stack.getUseDuration()/2) )
                {
                    stack.finishUsingItem(level,entity);
                    entity.stopUsingItem();
                }
            }

        });


        /*
        stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> {
            if (entity instanceof Player player) {
                if(!player.isSecondaryUseActive() && healing.target() == entity.getUUID())
                {

                    entity.stopUsingItem();
                    return;
                }
            }
            if(entity.getUseItemRemainingTicks() < 40 && healing.target() != entity.getUUID())
            {
                if(level instanceof ServerLevel serverlevel)
                {
                    LivingEntity patient = ((LivingEntity) serverlevel.getEntity(healing.target()));
                    if(patient.getHealth() < patient.getMaxHealth())
                    {
                        stack.shrink(1);
                        patient.heal(4);
                        entity.stopUsingItem();
                        if (entity instanceof Player player)
                        {
                            player.getCooldowns().addCooldown(this, 40);
                        }
                    }
                }
            }
        });

        */
        super.onUseTick(level, entity, stack, pRemainingUseDuration);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 80;
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> healing.remove());
        super.onStopUsing(stack, entity, count);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(entity instanceof Player playerwhoheals)
        {
            stack.getCapability(HealingCapability.CAPABILITY).ifPresent(healing -> {
                if(level instanceof ServerLevel serverlevel)
                {
                    LivingEntity gettingHealed;
                    if(healing.target() == null)
                    {
                        gettingHealed = playerwhoheals;
                    }
                    else
                    {
                        gettingHealed = ((LivingEntity) serverlevel.getEntity(healing.target()));
                    }

                    stack.shrink(1);
                    gettingHealed.heal(4);
                    healing.remove();
                    playerwhoheals.getCooldowns().addCooldown(this, 40);
                }
            });
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new HealingHandler(stack);
    }
}
