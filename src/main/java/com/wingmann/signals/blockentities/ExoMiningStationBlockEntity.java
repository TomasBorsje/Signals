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

    public static final int TAPE_SLOT_INDEX = 0;
    public static final int OUTPUT_SLOT_INDEX = 1;
    public static final int OUTPUT_SLOT_COUNT = 9;

    public ExoMiningStationBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.EXO_MINING_STATION_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    public void tickServer() {
        ItemStack tape = itemHandler.getStackInSlot(TAPE_SLOT_INDEX);
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
        assert level != null;
        assert level.getServer() != null;
        LootContext ctx = new LootContext.Builder((ServerLevel) level).withRandom(level.random).create(LootContextParamSet.builder().build());
        LootTable lootTable = level.getServer().getLootTables().get(new ResourceLocation(Signals.MODID, "drone/"+tag.getData().lootTableName));
        // Generate loot
        for (ItemStack stack : lootTable.getRandomItems(ctx)) {
            ItemStack remainder = tryOutputStack(stack);
            if(remainder != ItemStack.EMPTY) {
                spawnItemInWorld(remainder);
            }
        }
    }

    /**
     * Tries to add an itemstack to the output slots of the item handler.
     * @param stack The stack to try add
     * @return The remainder
     */
    private ItemStack tryOutputStack(ItemStack stack) {
        ItemStack remainder = stack;
        for (int i = OUTPUT_SLOT_INDEX; i < OUTPUT_SLOT_COUNT; i++) {
            remainder = itemHandler.insertItem(i, remainder, false);
            if(remainder == ItemStack.EMPTY) {
                break;
            }
        }
        return remainder;
    }

    private void spawnItemInWorld(ItemStack stack) {
        assert level != null;
        ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX()+0.5f, worldPosition.getY()+1, worldPosition.getZ()+0.5f, stack);
        float randomX = (level.random.nextFloat() - 0.5f) * 0.2f;
        float randomY = 1f;
        float randomZ = (level.random.nextFloat() - 0.5f) * 0.2f;
        itemEntity.setDeltaMovement(randomX, randomY, randomZ);
        level.addFreshEntity(itemEntity);
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
        return new ItemStackHandler(1 + OUTPUT_SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == TAPE_SLOT_INDEX) {
                    return isTapeItem(stack);
                }
                else return slot >= OUTPUT_SLOT_INDEX && slot < OUTPUT_SLOT_INDEX + OUTPUT_SLOT_COUNT;
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
