package com.jetpacker06.stainless;

import com.jetpacker06.stainless.block.AllBlocks;
import com.jetpacker06.stainless.block.StainlessBlockEntities;
import com.jetpacker06.stainless.item.AllItems;
import com.jetpacker06.stainless.recipe.RecipeTypes;
import com.jetpacker06.stainless.screen.AlloyBlasterScreen;
import com.jetpacker06.stainless.screen.MenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("stainless")
public class Stainless {
    public static final String MOD_ID = "stainless";
    private static final Logger LOGGER = LogManager.getLogger();

    public Stainless() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AllItems.register(eventBus);
        AllBlocks.register(eventBus);
        StainlessBlockEntities.register(eventBus);
        MenuTypes.register(eventBus);
        RecipeTypes.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypes.ALLOY_BLASTER_MENU.get(), AlloyBlasterScreen::new);
    }
}
