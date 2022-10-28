package com.wingmann.signals.registry;

import net.minecraft.util.RandomSource;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores signal data, populated during mod loading.
 */
public class SignalDataRegistry {
    private static final SignalDataRegistry INSTANCE = new SignalDataRegistry();

    private final Map<String, SignalData> signalDataMap = new LinkedHashMap<String, SignalData>();

    private final SignalData unknownSignal = new SignalData("unknown", "signals.signal.unknown_signal", "signals.signal.unknown_signal_description", "signals:unknown", 1);

    public static SignalDataRegistry getRegistry() {
        return INSTANCE;
    }

    public void registerSignalData(SignalData signalData) {
        signalDataMap.put(signalData.id, signalData);
    }

    private SignalDataRegistry() {
        // Random signals
        registerSignalData(new SignalData("basic1", "signals.signal.basic1", "signals.signal.basic1_description", "signals:basic1", 2.5f));
        registerSignalData(new SignalData("basic2", "signals.signal.basic2", "signals.signal.basic2_description", "signals:basic2", 1.1f));
    }
    public SignalData getRandomSignalData(RandomSource random) {
        if(signalDataMap.isEmpty()) {
            return unknownSignal;
        }
        return (SignalData) signalDataMap.values().toArray()[random.nextInt(signalDataMap.size())];
    }

    public SignalData getSignalData(String id) {
        // return unknown signal if id doesn't exist
        return signalDataMap.getOrDefault(id, unknownSignal);
    }

    public Map<String, SignalData> getRegisteredSignals() {
        return Collections.unmodifiableMap(signalDataMap);
    }
}
