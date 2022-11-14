package com.wingmann.signals.worldgen;

import com.wingmann.signals.setup.Registration;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ores {

    @NotNull
    public static PlacedFeature createOverworldOregen() {
        OreConfiguration config = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ALUMINIUM_ORE.get().defaultBlockState(), 5);
        return createPlacedFeature(new ConfiguredFeature<>(Feature.ORE, config),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                // TODO Replace
                new DimensionBiomeFilter(key -> key != null),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature createPlacedFeature(ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return new PlacedFeature(Holder.hackyErase(Holder.direct(feature)), List.copyOf(List.of(placementModifiers)));
    }
}
