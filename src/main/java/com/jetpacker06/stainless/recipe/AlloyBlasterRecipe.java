package com.jetpacker06.stainless.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jetpacker06.stainless.Stainless;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import javax.annotation.Nullable;

public class AlloyBlasterRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final int outputCount;
    private final NonNullList<Ingredient> recipeItems;
    private final int ing1count;
    private final int ing2count;
    private final int ing3count;



    public AlloyBlasterRecipe(ResourceLocation id, ItemStack output, int outputCount,
                              NonNullList<Ingredient> recipeItems, int ing1count, int ing2count, int ing3count) {
        this.id = id;
        this.output = output;
        this.outputCount = outputCount;
        this.recipeItems = recipeItems;
        this.ing1count =  ing1count;
        this.ing2count =  ing2count;
        this.ing3count =  ing3count;
    }
    @Override
    public boolean matches(SimpleContainer pContainer, net.minecraft.world.level.Level pLevel) {
        if(recipeItems.get(0).test(pContainer.getItem(1))) {
            return recipeItems.get(1).test(pContainer.getItem(2));
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }
    public int getOutputCount() {
        return outputCount;
    }
    public int getIng1count() {
        return ing1count;
    }
    public int getIng2count() {
        return ing2count;
    }
    public int getIng3count() {
        return ing3count;
    }
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlloyBlasterRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "alloy_blasting";
    }
    public static class Serializer implements RecipeSerializer<AlloyBlasterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Stainless.MOD_ID,"alloy_blasting");

        @Override
        public AlloyBlasterRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);
            for (int i = 0;i<ingredients.size();i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            int ing1count = GsonHelper.getAsInt(json, "ingredient1count");
            int ing2count = GsonHelper.getAsInt(json, "ingredient2count");
            int ing3count = GsonHelper.getAsInt(json, "ingredient3count");
            int outputCount = GsonHelper.getAsInt(json, "outputcount");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            return new AlloyBlasterRecipe(id, output, outputCount, inputs, ing1count, ing2count, ing3count);
        }

        @Override
        public AlloyBlasterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            inputs.set(0, Ingredient.fromNetwork(buf));
            inputs.set(1, Ingredient.fromNetwork(buf));
            inputs.set(2, Ingredient.fromNetwork(buf));
            int ing1count = buf.readInt();
            int ing2count = buf.readInt();
            int ing3count = buf.readInt();
            ItemStack output = buf.readItem();
            int outputCount = buf.readInt();
            return new AlloyBlasterRecipe(id, output, outputCount, inputs, ing1count, ing2count, ing3count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlloyBlasterRecipe recipe) {
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeInt(recipe.getIng1count());
            buf.writeInt(recipe.getIng2count());
            buf.writeInt(recipe.getIng3count());
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.getOutputCount());
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
