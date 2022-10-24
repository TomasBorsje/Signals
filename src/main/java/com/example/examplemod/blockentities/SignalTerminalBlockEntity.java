package com.example.examplemod.blockentities;

import com.example.examplemod.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SignalTerminalBlockEntity extends BlockEntity {

    public SignalTerminalBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.SIGNAL_TERMINAL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    public void tickServer() {

    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }
}
