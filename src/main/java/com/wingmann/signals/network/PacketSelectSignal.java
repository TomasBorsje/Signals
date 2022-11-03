package com.wingmann.signals.network;

import com.wingmann.signals.blockentities.SignalLocatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSelectSignal {
    private final int selectedSignalIndex;
    private final BlockPos signalLocatorBlockPos;

    public PacketSelectSignal(int selectedSignalIndex, BlockPos signalLocatorBlockPos) {
        this.selectedSignalIndex = selectedSignalIndex;
        this.signalLocatorBlockPos = signalLocatorBlockPos;
    }

    public PacketSelectSignal(FriendlyByteBuf buf) {
        this.selectedSignalIndex = buf.readInt();
        this.signalLocatorBlockPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.selectedSignalIndex);
        buf.writeBlockPos(this.signalLocatorBlockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are server side
            System.out.println("Received PacketSelectSignal on serverside.");
            // Print packet data
            System.out.println("selectedSignalIndex: " + this.selectedSignalIndex);
            System.out.println("signalLocatorBlockPos: " + this.signalLocatorBlockPos.toShortString());
            BlockEntity entity = ctx.getSender().getLevel().getBlockEntity(this.signalLocatorBlockPos);
            if(entity instanceof SignalLocatorBlockEntity) {
                ((SignalLocatorBlockEntity)entity).trySetTapeData();
                System.out.println("Set tape data serverside.");
            }
        });
        return true;
    }
}
