package com.wingmann.signals.util;

import com.wingmann.signals.registry.SignalData;
import com.wingmann.signals.registry.SignalDataRegistry;
import net.minecraft.nbt.CompoundTag;

public class TapeTag {
    public float downloadProgress = 0;
    public float filterProgress = 0;
    public String signalId = "unknown";
    private final CompoundTag internalTag;

    /**
     * Create a tape tag with no data.
     */
    public TapeTag() {
        internalTag = new CompoundTag();
    }

    /**
     * Create a tape tag from a compound tag.
     * @param tag The compound tag to create from
     */
    public TapeTag(CompoundTag tag) {
        internalTag = tag;
        if(tag.contains("downloadProgress")) {
            downloadProgress = tag.getFloat("downloadProgress");
        }
        if(tag.contains("filterProgress")) {
            filterProgress = tag.getFloat("filterProgress");
        }
        if(tag.contains("signalId")) {
            signalId = tag.getString("signalId");
        }
    }

    /**
     * Get the signal data for this tape.
     */
    public SignalData getData() {
        return SignalDataRegistry.getRegistry().getSignalData(signalId);
    }

    public CompoundTag toCompoundTag() {
        internalTag.putFloat("downloadProgress", downloadProgress);
        internalTag.putFloat("filterProgress", filterProgress);
        internalTag.putString("signalId", signalId);
        return internalTag;
    }
    public boolean isEmpty() {
        return signalId.equals("unknown");
    }
    public static boolean isValidTapeTag(CompoundTag other) {
        return other != null && !other.isEmpty() && other.contains("downloadProgress") && other.contains("filterProgress") && other.contains("signalId");
    }
}
