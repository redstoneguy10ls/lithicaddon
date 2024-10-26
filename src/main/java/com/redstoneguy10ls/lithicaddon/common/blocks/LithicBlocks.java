package com.redstoneguy10ls.lithicaddon.common.blocks;

import com.redstoneguy10ls.lithicaddon.common.blockentities.LithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blockentities.MothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.blocks.plants.LithicFruitBlocks;
import com.redstoneguy10ls.lithicaddon.common.fluids.LithicAcids;
import com.redstoneguy10ls.lithicaddon.common.fluids.LithicFluids;
import com.redstoneguy10ls.lithicaddon.common.fluids.LithicGlass;
import com.redstoneguy10ls.lithicaddon.common.fluids.LithicMetals;
import com.redstoneguy10ls.lithicaddon.common.items.LithicItems;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.CandleBlockItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class LithicBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);

    public static final Map<LithicMetals, RegistryObject<LiquidBlock>> METAL_FLUIDS = Helpers.mapOfKeys(LithicMetals.class, metal ->
            registerNoItem("fluid/metal/" + metal.toString(), () -> new
                    LiquidBlock(LithicFluids.METALS.get(metal).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()))
    );

    public static final Map<LithicAcids, RegistryObject<LiquidBlock>> ACID_FLUIDS = Helpers.mapOfKeys(LithicAcids.class, acid ->
            registerNoItem("fluid/acid/" + acid.toString(), () -> new
                    LiquidBlock(LithicFluids.ACIDS.get(acid).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()))
    );

    public static final Map<LithicGlass, RegistryObject<LiquidBlock>> GLASS_FLUIDS = Helpers.mapOfKeys(LithicGlass.class, glass ->
            registerNoItem("fluid/glass/" + glass.toString(), () -> new
                    LiquidBlock(LithicFluids.GLASS.get(glass).source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()))
    );
    public static final Map<LithicFruitBlocks.Tree, RegistryObject<Block>> FRUIT_TREE_LEAVES =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> register("plant/" + tree.name() + "_leaves", tree::createLeaves));
    public static final Map<LithicFruitBlocks.Tree, RegistryObject<Block>> FRUIT_TREE_BRANCHES =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> register("plant/" + tree.name() + "_branch", tree::createBranch));
    public static final Map<LithicFruitBlocks.Tree, RegistryObject<Block>> FRUIT_TREE_GROWING_BRANCHES =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> register("plant/" + tree.name() + "_growing_branch", tree::createGrowingBranch));

    public static final Map<LithicFruitBlocks.Tree, RegistryObject<Block>> FRUIT_TREE_SAPLINGS =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> register("plant/" + tree.name() + "_sapling", tree::createSapling));

    public static final Map<LithicFruitBlocks.Tree, RegistryObject<Block>> FRUIT_TREE_POTTED_SAPLINGS =
            Helpers.mapOfKeys(LithicFruitBlocks.Tree.class, tree -> registerNoItem("plant/potted/" + tree.name() + "_sapling", tree::createPottedSapling));


    public static final RegistryObject<Block> SHH = register("shh", () -> new shh(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .forceSolidOff()
            .noCollission()
            .strength(0.1f)
            .sound(SoundType.WOOL)

            ));




    public static final RegistryObject<Block> LCANDLE_HOLDER = register("candle_holder",
            () -> new lcandleHolderBlock(ExtendedProperties.of(Blocks.CANDLE)
                    .mapColor(MapColor.SAND)
                    .randomTicks()
                    .noOcclusion()
                    .strength(0.1F)
                    .sound(SoundType.CANDLE)
                    .lightLevel(lcandleHolderBlock.LIGHTING_SCALE)
                    .blockEntity(TFCBlockEntities.TICK_COUNTER))
            , b -> new CandleBlockItem(new Item.Properties(), b, TFCBlocks.CANDLE_CAKE));

    public static final RegistryObject<Block> MOTHBOX = register("mothbox",
            () -> new MothboxBlock(ExtendedProperties.of()
                    .strength(0.6f)
                    .sound(SoundType.WOOD)
                    .flammable(60, 30)
                    .randomTicks()
                    .blockEntity(LithicBlockEntities.MOTHBOX)
                    .serverTicks(MothBlockEntity::serverTick)));




    //BlockBehaviour.Properties.of().mapColor(rock.color()).instrument(NoteBlockInstrument.BASEDRUM).strength(2f)
    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(LithicBlocks.BLOCKS, LithicItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
