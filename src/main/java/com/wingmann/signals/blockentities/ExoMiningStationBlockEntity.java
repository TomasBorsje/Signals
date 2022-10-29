package com.wingmann.signals.blockentities;

import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
import com.wingmann.signals.util.TapeTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.wingmann.signals.items.TapeItem.isTapeItem;

public class ExoMiningStationBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public ExoMiningStationBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.EXO_MINING_STATION_BLOCK_ENTITY.get(), pos, state);
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
            if(!tag.isEmpty() && tag.downloadProgress == 1) {
                generateLoot(tag);
                itemHandler.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
    }

    /**
     * Reads the loot table for the given tag/signal data and creates the items.
     * @param tag The tag to generate the loot table for.
     */
    private void generateLoot(TapeTag tag) {
        // TODO: Explode loot everywhere!!
        assert level != null;
        assert level.getServer() != null;
        LootContext ctx = new LootContext.Builder((ServerLevel) level).withRandom(level.random).create(LootContextParamSet.builder().build());
        LootTable lootTable = level.getServer().getLootTables().get(new ResourceLocation(Signals.MODID, "drone/"+tag.getData().lootTableName));
        // Print found loot table
        System.out.println("Generating loot table: " + lootTable.getLootTableId());
        // Generate loot
        for (ItemStack stack : lootTable.getRandomItems(ctx)) {
            System.out.println("Generated loot: " + stack);
            ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX()+0.5f, worldPosition.getY()+1, worldPosition.getZ()+0.5f, stack);
            float randomX = (level.random.nextFloat() - 0.5f) * 0.2f;
            float randomY = 1f;
            float randomZ = (level.random.nextFloat() - 0.5f) * 0.2f;
            itemEntity.setDeltaMovement(randomX, randomY, randomZ);
            level.addFreshEntity(itemEntity);
        }
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
