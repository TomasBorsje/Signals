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

    private final SignalData unknownSignal = new SignalData("unknown", "signals.signal.unknown_signal", "signals.signal.unknown_signal_description", "signals:unknown", 1, "unknown", 0);

    public static SignalDataRegistry getRegistry() {
        return INSTANCE;
    }


    private SignalDataRegistry() {
        // Random signals
        registerSignalData(new SignalData("basic1", "signals.signal.basic1", "signals.signal.basic1_description", "basic1", 2.5f, "asteroid", 1));
        registerSignalData(new SignalData("basic2", "signals.signal.basic2", "signals.signal.basic2_description", "basic2", 1.1f, "planet", 0));
        registerSignalData(new SignalData("basic3", "signals.signal.basic2", "signals.signal.basic2_description", "basic2", 1.1f, "planet", 2));
        registerSignalData(new SignalData("basic4", "signals.signal.basic2", "signals.signal.basic2_description", "basic2", 1.1f, "planet", 3));
        registerSignalData(new SignalData("basic5", "signals.signal.basic2", "signals.signal.basic2_description", "basic2", 1.1f, "planet", 4));
    }
    public SignalData getRandomSignalData(RandomSource random) {
        if(signalDataMap.isEmpty()) {
            return unknownSignal;
        }
        return (SignalData) signalDataMap.values().toArray()[random.nextInt(signalDataMap.size())];
    }

    /**
     * Adds signal data to the registry using its id.
     * @param data The signal data to add
     */
    public void registerSignalData(SignalData data) {
        if(data != null && data.id != null) {
            signalDataMap.put(data.id, data);
        }
    }

    public SignalData getSignalData(String id) {
        // return unknown signal if id doesn't exist
        return signalDataMap.getOrDefault(id, unknownSignal);
    }

    public Map<String, SignalData> getRegisteredSignals() {
        return Collections.unmodifiableMap(signalDataMap);
    }
}
