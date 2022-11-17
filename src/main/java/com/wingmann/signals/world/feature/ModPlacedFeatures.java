package com.wingmann.signals.world.feature;

import com.wingmann.signals.Signals;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Signals.MODID);

    // 12 veins per chunk between 0-64 y level, maximum at 32y
    public static final RegistryObject<PlacedFeature> ALUMINIUM_ORE_PLACED = PLACED_FEATURES.register("aluminium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ALUMINIUM_ORE.getHolder().get(),
                    commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)))));

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus bus) {
        PLACED_FEATURES.register(bus);
    }

}
