package com.jetpacker06.stainless.block;

import com.jetpacker06.stainless.Stainless;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StainlessBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Stainless.MOD_ID);
    public static final RegistryObject<BlockEntityType<AlloyBlasterBlockEntity>> ALLOY_BLASTER =
            BLOCK_ENTITIES.register("alloy_blaster", () ->
            BlockEntityType.Builder.of(AlloyBlasterBlockEntity::new,
                    StainlessBlocks.ALLOY_BLASTER.get()).build(null));
    public static void register(IEventBus eventBus) {BLOCK_ENTITIES.register(eventBus);}
}