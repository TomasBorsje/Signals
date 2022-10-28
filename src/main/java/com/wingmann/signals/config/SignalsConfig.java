package com.wingmann.signals.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class SignalsConfig {

    public static ForgeConfigSpec.DoubleValue GLOBAL_DOWNLOAD_SPEED_MULTIPLIER;
    public static void register() {
        registerServerConfigs();
        registerCommonConfigs();
        registerClientConfigs();
    }

    private static void registerClientConfigs() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    private static void registerCommonConfigs() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

    private static void registerServerConfigs() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        GLOBAL_DOWNLOAD_SPEED_MULTIPLIER = SERVER_BUILDER
                .comment("The global download rate multiplier for all signals")
                        .defineInRange("global_download_multiplier", 1.0, 0.0, 1000.0);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
