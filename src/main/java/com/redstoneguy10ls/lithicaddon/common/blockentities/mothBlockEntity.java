package com.redstoneguy10ls.lithicaddon.common.blockentities;

import com.redstoneguy10ls.lithicaddon.common.blocks.mothboxBlock;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.IMoth;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothAbility;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.container.mothboxContainer;
import com.redstoneguy10ls.lithicaddon.config.lithicConfig;
import com.redstoneguy10ls.lithicaddon.util.lithicTags;
import net.dries007.tfc.common.blockentities.TickableInventoryBlockEntity;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class mothBlockEntity extends TickableInventoryBlockEntity<ItemStackHandler> implements ICalendarTickable {


    public static void serverTick(Level level, BlockPos pos, BlockState state, mothBlockEntity box)
    {
        box.checkForLastTickSync();
        box.checkForCalendarUpdate();

        if (level.getGameTime() % 60 == 0)
        {
            box.updateState();
        }
    }

    public static final int MIN_LIGHT_LEVEL = Helpers.getValueOrDefault(lithicConfig.SERVER.minLightLevel);

    public static final int MIN_LIGHTS = Helpers.getValueOrDefault(lithicConfig.SERVER.minLights);

    public static final float MIN_TEMP = Helpers.getValueOrDefault(lithicConfig.SERVER.mintemp).floatValue();

    public static final float MAX_TEMP = Helpers.getValueOrDefault(lithicConfig.SERVER.maxtemp).floatValue();

    public static final float MIN_RAIN = Helpers.getValueOrDefault(lithicConfig.SERVER.minrain).floatValue();

    public static final float MAX_RAIN = Helpers.getValueOrDefault(lithicConfig.SERVER.maxrain).floatValue();

    public static final int UPDATE_INTERVAL = ICalendar.TICKS_IN_DAY;
    public static final int SLOTS = 5;

    private static final Component NAME = Component.translatable(MOD_ID + ".block_entity.mothbox");

    private final IMoth[] cachedMoths;

    private long lastPlayerTick, lastAreaTick;

    private int leaves;

    public mothBlockEntity(BlockPos pos, BlockState state) {
        super(lithicBlockEntities.MOTHBOX.get(), pos, state, defaultInventory(SLOTS), NAME);
        lastPlayerTick = Integer.MIN_VALUE;
        lastAreaTick = Calendars.SERVER.getTicks();
        cachedMoths = new IMoth[] {null,null,null,null,null};
        leaves = 0;

        sidedInventory
                .on(new PartialItemHandler(inventory).insert(0), Direction.Plane.VERTICAL)
                .on(new PartialItemHandler(inventory).insert(1, 2, 3, 4), Direction.Plane.HORIZONTAL)
                .on(new PartialItemHandler(inventory).extract(0, 1, 2, 3, 4), Direction.DOWN);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        super.saveAdditional(nbt);
        nbt.putLong("lastTick", lastPlayerTick);
        nbt.putLong("lastAreaTick", lastAreaTick);
        nbt.putInt("leaves", leaves);
    }

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        super.loadAdditional(nbt);
        updateCache();
        lastPlayerTick = nbt.getLong("lastTick");
        lastAreaTick = nbt.getLong("lastAreaTick");
        leaves = nbt.getInt("leaves");

    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player) {
        return mothboxContainer.create(this, inv, windowID);
    }

    @Override
    public void onCalendarUpdate(long ticks) {tryPeriodicUpdate();}

    public void tryPeriodicUpdate()
    {
        long now = Calendars.SERVER.getTicks();
        if (now > (lastAreaTick + UPDATE_INTERVAL))
        {
            while (lastAreaTick < now)
            {
                updateTick();
                lastAreaTick += UPDATE_INTERVAL;
            }
            markForSync();
        }
    }
    public boolean isSlotEmpty(int slot)
    {
        return inventory.getStackInSlot(slot).isEmpty();
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        super.setAndUpdateSlots(slot);
        updateCache();
    }

    private void updateCache()
    {
        for(int i = 1; i < SLOTS; i++)
        {
            cachedMoths[i] = getMoth(i);
        }
    }

    public IMoth[] getCachedMoths()
    {
        if (level != null && level.isClientSide) updateCache();
        return cachedMoths;
    }
//TODO make moths not grow while climate is wrong
    //TODO refactor all of this honestly
    private void updateTick()
    {
        assert level != null;
        final float temp = Climate.getTemperature(level, worldPosition);
        final float rainfall = Climate.getRainfall(level, worldPosition);

        final List<IMoth> usableMoths = getUsableMoths(temp,rainfall);
        setLeaves(calculateLeaves());

        int bonus;

        final int light = getLight();
        final int breedTickChanceInverted = getBreedTickChanceInverted(usableMoths,light);
        //check if the conditions are right and if so, start the moth spawning process
        if (light > MIN_LIGHTS && (breedTickChanceInverted == 0 || level.random.nextInt(breedTickChanceInverted) == 0) && leaves > 0)
        {
            //Declares the parent variables
            IMoth parent1 = null;
            IMoth parent2 = null;
            //Declares the new moth
            IMoth uninitializedMoth = null;
            for (int i = 1; i < SLOTS; i++)
            {
                //check if the current lattice has the moth capability,
                //basically just checking each slot
                final IMoth moth = inventory.getStackInSlot(i).getCapability(MothCapability.CAPABILITY).resolve().orElse(null);
                //if the current slot has the moth capability then do stuff to that item stack
                if (moth != null)
                {
                    //larva stuff
                    if (moth.hasLarva())
                    {
                        //the bonuses for the moth having the fasting and hunger trait
                        bonus = 0;
                        bonus += moth.getAbility(MothAbility.FASTING);
                        bonus -= moth.getAbility(MothAbility.HUNGER);

                        //if its not a cocoon, eat leaves
                        if((moth.getDaysTillCocoon()+bonus > moth.daysAlive()) ||moth.isMoth())
                        {
                            eatLeaves(moth,level.random);
                        }//if it is a cocoon set as so
                        else if(moth.getDaysTillCocoon()+bonus <= moth.daysAlive())
                        {
                            moth.setHasCocoon(true);
                        }
                        //if its time to become a moth set as true
                        else if (moth.getDaysTillMoth()+bonus <= moth.daysAlive())
                        {
                            moth.setIsMoth(true);
                        }
                        //if its an adult set it as a parent
                        if (moth.isMoth())
                        {
                            if(parent1 == null) parent1 = moth;
                            else if(parent2 == null) parent2 = moth;
                        }
                        moth.setDaysAlive(moth.daysAlive() + 1);

                    }
                    //if its not a larva and it's not initialized, set uninitializedMoth to the current "moth"
                    else if (uninitializedMoth == null)
                    {
                        uninitializedMoth = moth;
                    }
                }
            }
            //
            if (uninitializedMoth != null)
            {
                if(parent2 == null)// if we have one or no parents
                {
                    uninitializedMoth.initFreshAbilities(level.random);
                }
                else if (parent1.isMoth() && parent2.isMoth())
                {
                    uninitializedMoth.setAbilitiesFromParents(parent1,parent2, level.random);
                }
            }
        }
        else//as lights are just for attracting the moths to lay eggs. they should still grow even when there's no light
        {
            if(leaves > 0)
            {
                for (int i = 1; i < SLOTS; i++)
                {
                    final IMoth moth = inventory.getStackInSlot(i).getCapability(MothCapability.CAPABILITY).resolve().orElse(null);
                    if (moth !=null && moth.hasLarva())
                    {
                        //the bonuses for the moth having the fasting and hunger trait
                        bonus = 0;
                        bonus += moth.getAbility(MothAbility.FASTING);
                        bonus -= moth.getAbility(MothAbility.HUNGER);

                        //if its not a cocoon, eat leaves
                        if((moth.getDaysTillCocoon()+bonus > moth.daysAlive()) || moth.isMoth())
                        {
                            eatLeaves(moth,level.random);
                        }//if it is a cocoon set as so
                        else if(moth.getDaysTillCocoon()+bonus <= moth.daysAlive()&& moth.getDaysTillMoth()+bonus > moth.daysAlive())
                        {
                            moth.setHasCocoon(true);
                        }
                        //if its time to become a moth set as true
                        else if (moth.getDaysTillMoth()+bonus <= moth.daysAlive())
                        {
                            moth.setIsMoth(true);
                        }
                        moth.setDaysAlive(moth.daysAlive() + 1);

                    }
                }
            }
        }
    }



    public int getLight()
    {
        assert level != null;
        int lights = 0;
        final BlockPos min = worldPosition.offset(-5, -5, -5);
        final BlockPos max = worldPosition.offset(5, 5, 5);
        if(level.hasChunksAt(min, max))
        {
            for (BlockPos pos : BlockPos.betweenClosed(min,max))
            {
                final BlockState state = level.getBlockState(pos.offset(0,-1,0));
                //final BlockState state = level.getBlockState(pos);

                if(level.getBrightness(LightLayer.BLOCK, pos) >= MIN_LIGHT_LEVEL){
                    //(state.getBlock() instanceof HalfTransparentBlock) || (state.getBlock() instanceof AirBlock)
                    if(state.propagatesSkylightDown(level, pos))
                    {

                    }
                    else
                    {
                        lights++;
                    }

                }
            }

        }
        return lights;
    }

    @NotNull
    public List<IMoth> getUsableMoths(float temp,float rain)
    {
        return Arrays.stream(cachedMoths).filter(moth -> moth != null && moth.hasLarva()
                && temp > MIN_TEMP+ MothAbility.getMinTemperature(moth.getAbility(MothAbility.HARDINESS))
                && temp < MAX_TEMP + MothAbility.getMaxTemperature(moth.getAbility(MothAbility.HARDINESS))
                && rain > MIN_RAIN + MothAbility.getMinRainfall(moth.getAbility(MothAbility.HARDINESS))
                && rain < MAX_RAIN + MothAbility.getMaxRainfall(moth.getAbility(MothAbility.HARDINESS))).collect(Collectors.toList());
    }


    public void setLeaves(int value)
    {
        leaves = value;
    }

    public void eatLeaves(IMoth moth, RandomSource random)
    {
        int eat = Mth.nextInt(random,0,100);
        int bonus = 0;
        bonus += 5*moth.getAbility(MothAbility.FASTING);
        bonus -= 5*moth.getAbility(MothAbility.HUNGER);


        if(eat < Helpers.getValueOrDefault(lithicConfig.SERVER.mothEatChance)+bonus)
        {
            inventory.getStackInSlot(0).shrink(1+ (moth.getAbility(MothAbility.MOTH_SIZE)/2));
        }
        setLeaves(calculateLeaves());

        /*
        int rands = rand.nextInt(100 + 1);
        if(rands < Helpers.getValueOrDefault(lithicConfig.SERVER.mothEatChance))
        {
            inventory.getStackInSlot(0).shrink(1);
        }
        setLeaves(calculateLeaves());

         */
    }

    public int calculateLeaves()
    {
        return inventory.getStackInSlot(0).getCount();
    }

    public int getLeaves()
    {
        calculateLeaves();
        return leaves;
    }

    public int getBreedTickChanceInverted(List<IMoth> moths ,int lights)
    {
        int chance = 0;
        int adultMoths=0;
        for(IMoth moth : moths)
        {
            if(moth.isMoth())
            {
                chance += 10 - moth.getAbility(MothAbility.FERTILITY);
                adultMoths++;
            }
        }
        //if theres aren't any "adult" moths give some chance
        if(adultMoths == 0)
        {
            chance = 80;
        }

        return Math.max(0, chance - Math.min(lights, 60));
    }

    public void updateState()
    {
        assert level != null;
        final boolean moths = hasMoths();
        final BlockState state = level.getBlockState(worldPosition);
        boolean hasLeaves = leaves > 0;
        if(hasLeaves != state.getValue(mothboxBlock.LARVA))
        {
            level.setBlockAndUpdate(worldPosition, state.setValue(mothboxBlock.LARVA, hasLeaves));
            markForSync();
        }
    }

    private boolean hasMoths()
    {
        for (int i = 1; i < SLOTS; i++)
        {
            if (cachedMoths[i] != null && cachedMoths[i].hasLarva())
            {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private IMoth getMoth(int slot)
    {
        final ItemStack stack = inventory.getStackInSlot(slot);
        if(!stack.isEmpty())
        {
            var opt = stack.getCapability(MothCapability.CAPABILITY).resolve();
            if(opt.isPresent())
            {
                return opt.get();
            }
        }
        return null;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        if(slot != 0)
        {
            return stack.getCapability(MothCapability.CAPABILITY).isPresent();
        }
        else
        {
            return Helpers.isItem(stack, lithicTags.Items.MOTH_FOOD);
        }
    }

    @Override
    public long getLastCalendarUpdateTick() {
        return 0;
    }

    @Override
    public void setLastCalendarUpdateTick(long l) {

    }
}
