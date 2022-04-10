package com.jetpacker06.stainless.item;

import com.jetpacker06.stainless.Stainless;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StainlessItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stainless.MOD_ID);
    //begin items
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new SteelIngotItem(new Item.Properties().tab(ItemGroups.STAINLESS)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(ItemGroups.STAINLESS)));

    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> (new SwordItem(SteelTier.STEEL, 3, -1.9f, new Item.Properties().tab(ItemGroups.STAINLESS))));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> (new AxeItem(SteelTier.STEEL, 5.5f, -3.1f, new Item.Properties().tab(ItemGroups.STAINLESS))));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> (new PickaxeItem(SteelTier.STEEL, 1, -2.8F, new Item.Properties().tab(ItemGroups.STAINLESS))));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> (new HoeItem(SteelTier.STEEL, -2, -.5f, new Item.Properties().tab(ItemGroups.STAINLESS))));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> (new ShovelItem(SteelTier.STEEL, 1.5f, -3f, new Item.Properties().tab(ItemGroups.STAINLESS))));
    //end items
    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
