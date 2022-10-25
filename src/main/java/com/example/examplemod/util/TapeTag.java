package com.example.examplemod.util;

import net.minecraft.nbt.CompoundTag;

public class TapeTag {
    public String signalName = "Missing Name";
    public String signalDescription = "Missing Description";
    public float downloadProgress = 0;
    public float filterProgress = 0;

    public int signalId = -1; // -1 signifies empty

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
        if(tag.contains("signalName")) {
            signalName = tag.getString("signalName");
        }
        if(tag.contains("signalDescription")) {
            signalDescription = tag.getString("signalDescription");
        }
        if(tag.contains("downloadProgress")) {
            downloadProgress = tag.getFloat("downloadProgress");
        }
        if(tag.contains("filterProgress")) {
            filterProgress = tag.getFloat("filterProgress");
        }
        if(tag.contains("signalId")) {
            signalId = tag.getInt("signalId");
        }
    }
    public CompoundTag toCompoundTag() {
        internalTag.putString("signalName", signalName);
        internalTag.putString("signalDescription", signalDescription);
        internalTag.putFloat("downloadProgress", downloadProgress);
        internalTag.putFloat("filterProgress", filterProgress);
        internalTag.putInt("signalId", signalId);
        return internalTag;
    }
    public boolean isEmpty() {
        return signalId == -1;
    }
    public static boolean isValidTapeTag(CompoundTag other) {
        return other != null && !other.isEmpty() && other.contains("signalName") && other.contains("signalDescription") && other.contains("downloadProgress") && other.contains("filterProgress")
                && other.contains("signalId");
    }
}
