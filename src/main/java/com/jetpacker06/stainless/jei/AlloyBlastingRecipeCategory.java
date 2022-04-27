package com.jetpacker06.stainless.jei;

import com.jetpacker06.stainless.Stainless;
import com.jetpacker06.stainless.block.AllBlocks;
import com.jetpacker06.stainless.recipe.AlloyBlasterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
@SuppressWarnings("removal")
public class AlloyBlastingRecipeCategory implements IRecipeCategory<AlloyBlasterRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Stainless.MOD_ID, "alloy_blasting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(Stainless.MOD_ID, "textures/gui/alloy_blaster_jei_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AlloyBlastingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(AllBlocks.ALLOY_BLASTER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AlloyBlasterRecipe> getRecipeClass() {
        return AlloyBlasterRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("jei_display_title");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull AlloyBlasterRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 86, 11).addIngredients(recipe.getIngredients().get(0));
        if (recipe.getIngredients().size() > 1) builder.addSlot(RecipeIngredientRole.INPUT, 86, 33).addIngredients(recipe.getIngredients().get(1));
        if (recipe.getIngredients().size() > 2) builder.addSlot(RecipeIngredientRole.INPUT, 86, 55).addIngredients(recipe.getIngredients().get(2));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 33).addItemStack(recipe.getResultItem());
    }
}
