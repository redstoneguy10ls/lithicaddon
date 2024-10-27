package com.redstoneguy10ls.lithicaddon.common.items;

import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.eerussianguy.firmalife.common.blocks.plant.FLFruitBlocks;
import com.redstoneguy10ls.lithicaddon.LithicAddon;
import com.redstoneguy10ls.lithicaddon.common.blocks.LithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.blocks.plants.LithicFruitBlocks;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class LithicTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final LithicTab.CreativeTabHolder LITHIC =
            register("lithic", () -> new ItemStack(LithicItems.LITHIC.get()), LithicTab::fillTab);

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {

        //accept(out, lithicItems.LITHIC);
        LithicItems.METAL_SPINDLES.values().forEach(reg -> accept(out, reg));
        LithicItems.SPINDLE_HEADS.values().forEach(reg -> accept(out, reg));

        accept(out, LithicItems.ALUMINUM_LID);
        accept(out, LithicItems.STAINLESS_STEEL_LID);
        accept(out, LithicItems.EMPTY_JAR_WITH_ALUMINUM_LID);
        accept(out, LithicItems.EMPTY_JAR_WITH_STAINLESS_STEEL_LID);

        for(Food food : Food.values())
        {
            accept(out, LithicItems.FRUIT_PRESERVES_ALUMINUM, food);
            accept(out, LithicItems.FRUIT_PRESERVES_STAINLESS_STEEL, food);

        }
        LithicItems.METAL_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));
        LithicItems.ACID_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));
        LithicItems.GLASS_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));



        accept(out, LithicItems.CHARCOAL_BRIQUETTE);
        accept(out, LithicItems.MOLD_INGOT);
        accept(out, LithicItems.MOLD_LAMP);

        accept(out, LithicItems.COCOON);
        accept(out, LithicItems.BOILED_COCOON);
        accept(out, LithicItems.BALL_OF_SILK);
        accept(out, LithicItems.LARVA_LATTICE);
        accept(out, LithicBlocks.MOTHBOX);

        LithicItems.FOODS.values().forEach(reg -> accept(out, reg));
    }

    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent out)
    {
        if (out.getTab() == TFCCreativeTabs.FLORA.tab().get())
        {

            for (LithicFruitBlocks.Tree tree : LithicFruitBlocks.Tree.values())
            {
                accept(out, LithicBlocks.FRUIT_TREE_SAPLINGS, tree);
                accept(out, LithicBlocks.FRUIT_TREE_LEAVES, tree);
            }
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }
    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static LithicTab.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("lithicaddon.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new LithicTab.CreativeTabHolder(reg, displayItems);
    }

    private static <T> void consumeOurs(IForgeRegistry<T> registry, Consumer<T> consumer)
    {
        for (T value : registry)
        {
            if (Objects.requireNonNull(registry.getKey(value)).getNamespace().equals(LithicAddon.MOD_ID))
            {
                consumer.accept(value);
            }
        }
    }
    public record CreativeTabHolder(RegistryObject<CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}
}
