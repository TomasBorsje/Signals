package com.wingmann.signals.blockentities;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.wingmann.signals.items.TapeItem.isTapeItem;

public class SignalLocatorBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final SignalData[] signalDataList = new SignalData[MAX_SIGNALS];
    public static final int MAX_SIGNALS = 3;

    public SignalLocatorBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.SIGNAL_LOCATOR_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    public boolean hasNonEmptyTape() {
        ItemStack stack = itemHandler.getStackInSlot(0);
        return isTapeItem(stack) && TapeTag.isValidTapeTag(stack.getTag()) && !(new TapeTag(stack.getTag()).isEmpty());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        refreshSignalList();
    }

    /**
     * Replaces all existing signals with random ones.
     */
    public void refreshSignalList() {
        if(level == null || level.isClientSide) return;
        for(int i = 0; i < MAX_SIGNALS; i++) {
            System.out.println("Refreshed data list!");
            signalDataList[i] = SignalDataRegistry.getRegistry().getRandomSignalData(level.random);
        }
    }

    public List<SignalData> getSignalDataList() {
        return Collections.unmodifiableList(Arrays.stream(signalDataList).toList());
    }

    /**
     * Attempts to set the current tape's data to a random signal.
     */
    public void trySetTapeData() {
        ItemStack tape = itemHandler.getStackInSlot(0);
        if (tape.getItem() == Registration.TAPE_ITEM.get() && TapeTag.isValidTapeTag(tape.getTag())) {
            // We have a valid tape + tape NBT
            TapeTag tag = new TapeTag(tape.getTag());
            if(tag.isEmpty()) {
                // Empty data, get random signal and assign it to the tape
                assert level != null;
                SignalData signalData = SignalDataRegistry.getRegistry().getRandomSignalData(level.random);
                tag.signalId = signalData.id;
            }
            tape.setTag(tag.toCompoundTag());
        }
    }

    public void tickServer() {
        // Nothing
        // TODO: Make signal locating take a few seconds?
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
