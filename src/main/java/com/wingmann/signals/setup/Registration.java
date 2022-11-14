package com.wingmann.signals.setup;

import com.wingmann.signals.Signals;
import com.wingmann.signals.blockentities.ExoMiningStationBlockEntity;
import com.wingmann.signals.blockentities.SignalLocatorBlockEntity;
import com.wingmann.signals.blockentities.SignalTerminalBlockEntity;
import com.wingmann.signals.blocks.ExoMiningStationBlock;
import com.wingmann.signals.blocks.SignalLocatorBlock;
import com.wingmann.signals.blocks.SignalTerminalBlock;
import com.wingmann.signals.containers.ExoMiningStationContainer;
import com.wingmann.signals.containers.SignalLocatorContainer;
import com.wingmann.signals.containers.SignalTerminalContainer;
import com.wingmann.signals.items.TapeItem;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Signals.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Signals.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Signals.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Signals.MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    /**
     * Utility method to get a BlockItem from a block.
     */
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

    // Some common properties for our blocks and items
    public static final BlockBehaviour.Properties METAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops();
    static final BlockBehaviour.Properties METAL_BLOCK_NO_OCCLUSION_PROPERTIES = METAL_BLOCK_PROPERTIES.noOcclusion();
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_TAB);
    public static final Item.Properties ITEM_PROPERTIES_64_STACK = new Item.Properties().tab(ModSetup.ITEM_TAB).stacksTo(64);

    ///// Signal Terminal /////
    public static final RegistryObject<Block> SIGNAL_TERMINAL = BLOCKS.register("signal_terminal", SignalTerminalBlock::new);
    public static final RegistryObject<Item> SIGNAL_TERMINAL_ITEM = fromBlock(SIGNAL_TERMINAL);
    public static final RegistryObject<BlockEntityType<SignalTerminalBlockEntity>> SIGNAL_TERMINAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("signal_terminal", () -> BlockEntityType.Builder.of(SignalTerminalBlockEntity::new, SIGNAL_TERMINAL.get()).build(null));
    public static final RegistryObject<MenuType<SignalTerminalContainer>> SIGNAL_TERMINAL_CONTAINER = CONTAINERS.register("signal_terminal",
            () -> IForgeMenuType.create((windowId, inv, data) -> new SignalTerminalContainer(windowId, data.readBlockPos(), inv, inv.player)));

    ///// Exo-Mining Station /////
    public static final RegistryObject<Block> EXO_MINING_STATION = BLOCKS.register("exo_mining_station", ExoMiningStationBlock::new);
    public static final RegistryObject<Item> EXO_MINING_STATION_ITEM = fromBlock(EXO_MINING_STATION);
    public static final RegistryObject<BlockEntityType<ExoMiningStationBlockEntity>> EXO_MINING_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("exo_mining_station", () -> BlockEntityType.Builder.of(ExoMiningStationBlockEntity::new, EXO_MINING_STATION.get()).build(null));
    public static final RegistryObject<MenuType<ExoMiningStationContainer>> EXO_MINING_STATION_CONTAINER = CONTAINERS.register("exo_mining_station",
            () -> IForgeMenuType.create((windowId, inv, data) -> new ExoMiningStationContainer(windowId, data.readBlockPos(), inv, inv.player)));

    ///// Signal Locator /////
    public static final RegistryObject<Block> SIGNAL_LOCATOR = BLOCKS.register("signal_locator", SignalLocatorBlock::new);
    public static final RegistryObject<Item> SIGNAL_LOCATOR_ITEM = fromBlock(SIGNAL_LOCATOR);
    public static final RegistryObject<BlockEntityType<SignalLocatorBlockEntity>> SIGNAL_LOCATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("signal_locator", () -> BlockEntityType.Builder.of(SignalLocatorBlockEntity::new, SIGNAL_LOCATOR.get()).build(null));
    public static final RegistryObject<MenuType<SignalLocatorContainer>> SIGNAL_LOCATOR_CONTAINER = CONTAINERS.register("signal_locator",
            () -> IForgeMenuType.create((windowId, inv, data) -> new SignalLocatorContainer(windowId, data.readBlockPos(), inv, inv.player)));

    ///// Normal Blocks /////
    public static final RegistryObject<Block> SATELLITE_DISH_FRAME = BLOCKS.register("satellite_dish_frame", () -> new Block(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_DISH_FRAME_ITEM = fromBlock(SATELLITE_DISH_FRAME);
    public static final RegistryObject<Block> SATELLITE_DISH_SUPPORT = BLOCKS.register("satellite_dish_support", () -> new Block(METAL_BLOCK_NO_OCCLUSION_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_DISH_SUPPORT_ITEM = fromBlock(SATELLITE_DISH_SUPPORT);
    public static final RegistryObject<Block> SATELLITE_ANTENNA = BLOCKS.register("satellite_antenna", () -> new Block(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_ANTENNA_ITEM = fromBlock(SATELLITE_ANTENNA);
    public static final RegistryObject<Block> ALUMINIUM_ORE = BLOCKS.register("aluminium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> ALUMINIUM_ORE_ITEM = fromBlock(ALUMINIUM_ORE);

    ///// Items /////
    public static final RegistryObject<Item> TAPE_ITEM = ITEMS.register("tape", TapeItem::new);
    public static final RegistryObject<Item> CIRCUIT_BOARD = ITEMS.register("circuit_board", () -> new Item(ITEM_PROPERTIES_64_STACK));
    // TODO: Add aluminium ore and chunk tags etc
    // TODO: Worldgen config for aluminium ore
    public static final RegistryObject<Item> ALUMINIUM_CHUNK = ITEMS.register("aluminium_chunk", () -> new Item(ITEM_PROPERTIES_64_STACK));
    public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(ITEM_PROPERTIES_64_STACK));
}
