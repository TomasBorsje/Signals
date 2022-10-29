package com.wingmann.signals.blockentities;

import com.wingmann.signals.config.SignalsConfig;
import com.wingmann.signals.registry.SignalData;
import com.wingmann.signals.registry.SignalDataRegistry;
import com.wingmann.signals.setup.Registration;
import com.wingmann.signals.util.TapeTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.wingmann.signals.items.TapeItem.isTapeItem;

public class SignalTerminalBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public SignalTerminalBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.SIGNAL_TERMINAL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    public void tickServer() {
        ItemStack tape = itemHandler.getStackInSlot(0);
        if (tape.getItem() == Registration.TAPE_ITEM.get() && TapeTag.isValidTapeTag(tape.getTag())) {
            // We have a valid tape + tape NBT
            TapeTag tag = new TapeTag(tape.getTag());
            if(tag.isEmpty()) {
                // Empty data
                // TODO: Generate or get signal data?
                assert level != null;
                // Get random signal and assign it to the tape
                SignalData signalData = SignalDataRegistry.getRegistry().getRandomSignalData(level.random);
                tag.signalId = signalData.id;
            } else {
                // Do processing
                tag.downloadProgress += 1f/200f * tag.getData().downloadTimeMultiplier * SignalsConfig.GLOBAL_DOWNLOAD_SPEED_MULTIPLIER.get(); // 10 seconds default * multiplier
                tag.downloadProgress = Math.min(tag.downloadProgress, 1);
            }
            tape.setTag(tag.toCompoundTag());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        super.load(tag);
    }

    public SignalData tryGetSignalData() {
        ItemStack tape = itemHandler.getStackInSlot(0);
        if(tape.getItem() == Registration.TAPE_ITEM.get() && TapeTag.isValidTapeTag(tape.getTag())) {
            TapeTag tapeTag = new TapeTag(tape.getTag());
            return tapeTag.getData();
        }
        return null;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isTapeItem(stack);
            }
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
