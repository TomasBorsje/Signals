package com.example.examplemod.blockentities;

import com.example.examplemod.items.TapeItem;
import com.example.examplemod.setup.Registration;
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

    }

    public static boolean isTapeItem(ItemStack stack) {
        return stack.getItem() instanceof TapeItem;
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        super.load(tag);
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

                // Update inserted tapes
                ItemStack stackInSlot = getStackInSlot(slot);
                if(stackInSlot != ItemStack.EMPTY && isTapeItem(stackInSlot)) {
                    if (stackInSlot.hasTag()) {
                        assert stackInSlot.getTag() != null;
                        stackInSlot.getTag().putString("lore", "Tag updated.");
                    } else {
                        CompoundTag tag = new CompoundTag();
                        tag.putString("lore", "Fresh tag.");
                        stackInSlot.setTag(tag);
                    }
                }
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
