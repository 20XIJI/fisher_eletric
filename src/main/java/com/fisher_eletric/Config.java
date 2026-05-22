package com.fisher_eletric;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec SPEC;

    public static ModConfigSpec.IntValue cooldownTicks;
    public static ModConfigSpec.IntValue waterSearchRadius;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        cooldownTicks = builder
                .comment("Ticks between each fishing attempt (20 ticks = 1 second)")
                .defineInRange("cooldownTicks", 20, 1, 200);

        waterSearchRadius = builder
                .comment("Radius (in blocks) to search for water around the player")
                .defineInRange("waterSearchRadius", 5, 1, 32);

        SPEC = builder.build();
    }
}
