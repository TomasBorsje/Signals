package com.wingmann.signals.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Signals.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> ALUMINIUM_ORE_CONFIG = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ALUMINIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.DEEPSLATE_ALUMINIUM_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ALUMINIUM_ORE = CONFIGURED_FEATURES.register("aluminium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ALUMINIUM_ORE_CONFIG.get(),9)));

    public static void register(IEventBus bus) {
        CONFIGURED_FEATURES.register(bus);
    }
}
