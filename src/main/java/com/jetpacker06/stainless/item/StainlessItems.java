package com.jetpacker06.stainless.item;

import com.jetpacker06.stainless.Stainless;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StainlessItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stainless.MOD_ID);
    //begin items
    public static final RegistryObject<Item> STEEL_INGOT =ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(ItemGroups.STAINLESS)));
    public static final RegistryObject<Item> STEEL_NUGGET =ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(ItemGroups.STAINLESS)));
    //end items
    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
