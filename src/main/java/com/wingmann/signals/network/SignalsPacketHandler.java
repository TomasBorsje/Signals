package com.wingmann.signals.network;

import com.wingmann.signals.Signals;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class SignalsPacketHandler {
    private static SimpleChannel INSTANCE;

    // Every packet needs a unique ID (unique for this channel)
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Signals.MODID, "main"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Register packet for selecting a signal with the signal locator
        net.messageBuilder(PacketStartSignalScan.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketStartSignalScan::new)
                .encoder(PacketStartSignalScan::toBytes)
                .consumer(PacketStartSignalScan::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToPlayersTrackingChunk(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> player.getLevel().getChunkAt(player.blockPosition())), message);
    }

    public static <MSG> void sendToPlayersTrackingChunk(MSG message, ServerPlayer player, BlockPos pos) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> player.getLevel().getChunkAt(pos)), message);
    }
}
