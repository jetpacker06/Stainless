package com.jetpacker06.stainless.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class SteelTier {
    public static final ForgeTier STEEL = new ForgeTier(
            2, 400, 7.0f, 2.5f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(StainlessItems.STEEL_INGOT.get()));
}
