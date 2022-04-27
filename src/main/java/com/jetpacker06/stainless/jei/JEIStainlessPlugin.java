package com.jetpacker06.stainless.jei;

import com.jetpacker06.stainless.Stainless;
import com.jetpacker06.stainless.block.AllBlocks;
import com.jetpacker06.stainless.recipe.AlloyBlasterRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;
@JeiPlugin
@SuppressWarnings("removal")
public class JEIStainlessPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(Stainless.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlloyBlastingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager m = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<AlloyBlasterRecipe> recipes = m.getAllRecipesFor(AlloyBlasterRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(AlloyBlastingRecipeCategory.UID, AlloyBlasterRecipe.class), recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AllBlocks.ALLOY_BLASTER.get()), AlloyBlastingRecipeCategory.UID);
    }
}
