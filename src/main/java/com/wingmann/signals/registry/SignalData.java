package com.wingmann.signals.registry;

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

    public SignalData(String id, String signalName, String signalDescription, String lootTableName,
                      float downloadTimeMultiplier) {
        this.id = id;
        this.signalName = signalName;
        this.signalDescription = signalDescription;
        this.lootTableName = lootTableName;
        this.downloadTimeMultiplier = downloadTimeMultiplier;
    }
}
