package com.jetpacker06.stainless;

import com.jetpacker06.stainless.block.StainlessBlocks;
import com.jetpacker06.stainless.item.StainlessItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("stainless")
public class Stainless {
    public static final String MOD_ID = "stainless";
    private static final Logger LOGGER = LogManager.getLogger();

    public Stainless() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        StainlessItems.register(eventBus);
        StainlessBlocks.register(eventBus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PRE INIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
