package com.redstoneguy10ls.lithicaddon.common.blocks;

import com.redstoneguy10ls.lithicaddon.common.blockentities.LithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blockentities.MothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.IMoth;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothAbility;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class WellPulleyBlock extends DeviceBlock {
    
    
    public WellPulleyBlock(ExtendedProperties properties) {
        super(properties, InventoryRemoveBehavior.DROP);
        //registerDefaultState(getStateDefinition().any().setValue(LARVA, false));
        
    }
    
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
                level.getBlockEntity(pos, LithicBlockEntities.MOTHBOX.get()).ifPresent(box -> Helpers.openScreen(serverPlayer, box, pos));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
            
        }
        return InteractionResult.PASS;
        
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.getBlockEntity(pos, LithicBlockEntities.MOTHBOX.get()).ifPresent(MothBlockEntity::tryPeriodicUpdate);
    }
    /*
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(LARVA));
    }
    
     */
}