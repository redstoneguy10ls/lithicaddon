package com.redstoneguy10ls.lithicaddon.common.fluids;


import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import net.dries007.tfc.common.fluids.*;
import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;
import static net.dries007.tfc.common.fluids.TFCFluids.*;

public final class LithicFluids {
    /*
        public static final Map<lithicMetals, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(lithicMetals.class, metal -> register(
            "metal/" + metal.getId(),
            properties -> properties
                    .block(lithicBlocks.METAL_FLUIDS.get(metal))
                    .bucket(lithicItems.FLUID_BUCKETS.get(FluidId.asType(metal)))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.metal." + metal.getId()),
            new FluidTypeClientProperties(ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));
     */

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MOD_ID);

    public static final Map<LithicMetals, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(LithicMetals.class, metal
            -> register(
            "metal/" + metal.getId(),
            properties -> properties
                    .block(LithicBlocks.METAL_FLUIDS.get(metal))
                    .bucket(LithicItems.METAL_FLUIDS_BUCKETS.get(metal))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.metal." + metal.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    public static final Map<LithicAcids, FluidRegistryObject<ForgeFlowingFluid>> ACIDS = Helpers.mapOfKeys(LithicAcids.class, acid
            -> register(
            "acid/" + acid.getId(),
            properties -> properties
                    .block(LithicBlocks.ACID_FLUIDS.get(acid))
                    .bucket(LithicItems.ACID_FLUIDS_BUCKETS.get(acid))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.acid." + acid.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | acid.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    public static final Map<LithicGlass, FluidRegistryObject<ForgeFlowingFluid>> GLASS = Helpers.mapOfKeys(LithicGlass.class, glass
            -> register(
            "glass/" + glass.getId(),
            properties -> properties
                    .block(LithicBlocks.GLASS_FLUIDS.get(glass))
                    .bucket(LithicItems.GLASS_FLUIDS_BUCKETS.get(glass))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.glass." + glass.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | glass.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));
    public static final Map<ExtraFluids, FluidRegistryObject<ForgeFlowingFluid>> EXTRA_FLUIDS = Helpers.mapOfKeys(ExtraFluids.class, fluid -> register(
            fluid.getSerializedName(),
            properties -> properties.block(LithicBlocks.EXTRA_FLUIDS.get(fluid)).bucket(LithicItems.EXTRA_FLUIDS_BUCKETS.get(fluid)),
            waterLike()
                    .descriptionId("fluid.lithic." + fluid.getSerializedName())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(fluid.getColor(), WATER_STILL, WATER_FLOW, WATER_OVERLAY, null),
            MixingFluid.Source::new,
            MixingFluid.Flowing::new
    ));


    private static FluidType.Properties lavaLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(0)
                .density(3000)
                .viscosity(6000)
                .temperature(1300)
                .canConvertToSource(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(false)
                .canSwim(false)
                .supportsBoating(false);
    }
    private static FluidType.Properties waterLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.WATER)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .canConvertToSource(true)
                .canDrown(true)
                .canExtinguish(true)
                .canHydrate(false)
                .canPushEntity(true)
                .canSwim(true)
                .supportsBoating(true);
    }


    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder,
                () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }


}
