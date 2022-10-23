package com.example.examplemod;

import com.example.examplemod.setup.ClientSetup;
import com.example.examplemod.setup.ModSetup;
import com.example.examplemod.setup.Registration;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Signals.MODID)
public class Signals
{
    public static final String MODID = "signals";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Signals()
    {
        Registration.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register 'ModSetup::init' to be called at mod setup time (server and client)
        modEventBus.addListener(ModSetup::init);
        // Register 'ClientSetup::init' to be called at mod setup time (client only)
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientSetup::init));
    }
}
