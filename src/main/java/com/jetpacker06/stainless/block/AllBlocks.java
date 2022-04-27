package com.jetpacker06.stainless.block;

import com.jetpacker06.stainless.Stainless;
import com.jetpacker06.stainless.item.ItemGroups;
import com.jetpacker06.stainless.item.AllItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stainless.MOD_ID);
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {RegistryObject<T> toReturn = BLOCKS.register(name, block);registerBlockItem(name, toReturn, tab);return toReturn;}
    //begin blocks

    public static final RegistryObject<Block>
        STEEL_BLOCK = metal_block("steel_block"),
        ALLOY_BLASTER = registerBlock("alloy_blaster", () -> new AlloyBlasterBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(6).explosionResistance(9).requiresCorrectToolForDrops()), ItemGroups.STAINLESS);

    //end blocks
    public static final RegistryObject<Block> metal_block(String name) {
        return registerBlock(name, () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(6).explosionResistance(9).requiresCorrectToolForDrops()), ItemGroups.STAINLESS);
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {return AllItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));}
    public static void register(IEventBus eventBus) {BLOCKS.register(eventBus);}
}
