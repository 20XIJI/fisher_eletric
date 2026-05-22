package com.fisher_eletric;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {

    static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, FisherEletric.MODID);

    static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FisherEletric.MODID);

    public static final Supplier<Item> ELECTRIC_FISHING_NET = ITEMS.register("electric_fishing_rod",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> FISH_CATCHER_ROD = ITEMS.register("fish_catcher_rod",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final Supplier<CreativeModeTab> FISHER_TAB = CREATIVE_MODE_TABS.register("fisher_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FISH_CATCHER_ROD.get()))
                    .title(Component.translatable("itemGroup.fisher_eletric"))
                    .displayItems((params, output) -> {
                        output.accept(ELECTRIC_FISHING_NET.get());
                        output.accept(FISH_CATCHER_ROD.get());
                    })
                    .build());
}
