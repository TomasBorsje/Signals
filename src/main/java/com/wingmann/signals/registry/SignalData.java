package com.wingmann.signals.registry;

import com.wingmann.signals.Signals;
import net.minecraft.resources.ResourceLocation;

public class SignalData {
    /**
     * ID of the signal. "unknown" is an unknown signal, and always present in the registry.
     */
    public final String id;
    /**
     * Name of the signal. Should be a lang key as it will be translated.
     */
    public final String signalName;
    /**
     * Description of the signal. Should be a lang key as it will be translated.
     */
    public final String signalDescription;
    /**
     * The name of the loot table that provides loot for this signal.
     * Will be generated upon a drone returning.
     */
    public final String lootTableName;

    /**
     * Multiplier of the download time; how long the signal takes to download.
     * Base download time is 10 seconds.
     */
    public final float downloadTimeMultiplier;

    public final String signalPreviewTexture;

    public final ResourceLocation signalPreviewResourceLocation;

    /**
     * The rarity of the signal.
     * 0 - Common (White)
     * 1 - Uncommon (Green)
     * 2 - Rare (Blue)
     * 3 - Epic (Purple)
     * 4 - Legendary (Orange)
     */
    public final int rarity;

    public SignalData(String id, String signalName, String signalDescription, String lootTableName,
                      float downloadTimeMultiplier, String signalPreviewTexture, int rarity) {
        this.id = id;
        this.signalName = signalName;
        this.signalDescription = signalDescription;
        this.lootTableName = lootTableName;
        this.downloadTimeMultiplier = downloadTimeMultiplier;
        this.signalPreviewTexture = signalPreviewTexture;
        this.signalPreviewResourceLocation = new ResourceLocation(Signals.MODID, "textures/gui/signalpreviews/"+signalPreviewTexture+".png");
        this.rarity = rarity;
    }

    /**
     * Returns the hex code for the colour of this signal's rarity.
     * @return Hex code for this signal's rarity
     */
    public int getRarityColour() {
        switch (rarity) {
            case 1:
                return 0x00FF00; // Green
            case 2:
                return 0x0000FF; // Blue
            case 3:
                return 0xFF00FF; // Purple
            case 4:
                return 0xFFA500; // Orange
            default:
                return 0xFFFFFF; // White
        }
    }
}
