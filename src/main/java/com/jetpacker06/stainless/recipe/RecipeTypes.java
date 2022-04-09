package com.jetpacker06.stainless.recipe;

import com.jetpacker06.stainless.Stainless;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Stainless.MOD_ID);

    public static final RegistryObject<RecipeSerializer<AlloyBlasterRecipe>> ALlOY_BLASTER_SERIALIZER =
            SERIALIZERS.register("alloy_blasting", () -> AlloyBlasterRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
