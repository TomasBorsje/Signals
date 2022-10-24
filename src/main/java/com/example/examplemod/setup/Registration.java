package com.example.examplemod.setup;

import com.example.examplemod.blockentities.SignalTerminalBlockEntity;
import com.example.examplemod.blocks.SignalTerminalBlock;
import com.example.examplemod.containers.SignalTerminalContainer;
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

import static com.example.examplemod.Signals.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

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

    ///// Machines /////
    public static final RegistryObject<Block> SIGNAL_TERMINAL = BLOCKS.register("signal_terminal", SignalTerminalBlock::new);
    public static final RegistryObject<Item> SIGNAL_TERMINAL_ITEM = fromBlock(SIGNAL_TERMINAL);
    public static final RegistryObject<BlockEntityType<SignalTerminalBlockEntity>> SIGNAL_TERMINAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("signal_terminal", () -> BlockEntityType.Builder.of(SignalTerminalBlockEntity::new, SIGNAL_TERMINAL.get()).build(null));
    public static final RegistryObject<MenuType<SignalTerminalContainer>> SIGNAL_TERMINAL_CONTAINER = CONTAINERS.register("powergen",
            () -> IForgeMenuType.create((windowId, inv, data) -> new SignalTerminalContainer(windowId, data.readBlockPos(), inv, inv.player)));

    ///// Normal Blocks /////
    public static final RegistryObject<Block> SATELLITE_DISH_FRAME = BLOCKS.register("satellite_dish_frame", () -> new Block(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_DISH_FRAME_ITEM = fromBlock(SATELLITE_DISH_FRAME);
    public static final RegistryObject<Block> SATELLITE_DISH_SUPPORT = BLOCKS.register("satellite_dish_support", () -> new Block(METAL_BLOCK_NO_OCCLUSION_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_DISH_SUPPORT_ITEM = fromBlock(SATELLITE_DISH_SUPPORT);
    public static final RegistryObject<Block> SATELLITE_ANTENNA = BLOCKS.register("satellite_antenna", () -> new Block(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SATELLITE_ANTENNA_ITEM = fromBlock(SATELLITE_ANTENNA);

}
