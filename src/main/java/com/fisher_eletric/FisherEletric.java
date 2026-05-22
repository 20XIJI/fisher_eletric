package com.fisher_eletric;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(FisherEletric.MODID)
public class FisherEletric {

    public static final String MODID = "fisher_eletric";

    public FisherEletric(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        ModItems.CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);

        ItemEventHandler handler = new ItemEventHandler();
        handler.register(new NetBehavior());
        handler.register(new RodBehavior());
        NeoForge.EVENT_BUS.register(handler);
    }
}
