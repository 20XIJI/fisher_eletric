package com.fisher_eletric;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec SPEC;

    public static ModConfigSpec.IntValue waterSearchRadius;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("New Energy Fishing Net settings");
        Net.build(builder);

        builder.comment("New Energy Fishing Rod settings");
        Rod.build(builder);

        waterSearchRadius = builder
                .comment("Shared radius (blocks) to search for water around the player")
                .defineInRange("waterSearchRadius", 5, 1, 32);

        SPEC = builder.build();
    }

    public static class Net {
        public static ModConfigSpec.IntValue cooldownTicks;

        static void build(ModConfigSpec.Builder builder) {
            cooldownTicks = builder
                    .comment("Ticks between each fishing attempt (20 ticks = 1 second)")
                    .defineInRange("netCooldownTicks", 20, 1, 200);
        }
    }

    public static class Rod {
        public static ModConfigSpec.IntValue spawnInterval;
        public static ModConfigSpec.IntValue lifetimeTicks;

        static void build(ModConfigSpec.Builder builder) {
            spawnInterval = builder
                    .comment("Ticks between each fish spawn (lower = faster stream)")
                    .defineInRange("rodSpawnInterval", 3, 1, 40);

            lifetimeTicks = builder
                    .comment("How many ticks spawned fish live (20 ticks = 1 second)")
                    .defineInRange("rodLifetimeTicks", 40, 5, 200);
        }
    }
}
