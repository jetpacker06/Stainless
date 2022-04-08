package com.jetpacker06.stainless.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroups {
    public static final CreativeModeTab STAINLESS = new CreativeModeTab("Stainless Items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(StainlessItems.STEEL_INGOT.get());
        }
    };
}