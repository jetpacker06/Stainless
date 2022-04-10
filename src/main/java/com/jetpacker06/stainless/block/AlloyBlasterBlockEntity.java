package com.jetpacker06.stainless.block;

import com.jetpacker06.stainless.recipe.AlloyBlasterRecipe;
import com.jetpacker06.stainless.screen.AlloyBlasterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AlloyBlasterBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public AlloyBlasterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(StainlessBlockEntities.ALLOY_BLASTER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return AlloyBlasterBlockEntity.this.progress;
                    case 1: return AlloyBlasterBlockEntity.this.maxProgress;
                    case 2: return AlloyBlasterBlockEntity.this.fuelTime;
                    case 3: return AlloyBlasterBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AlloyBlasterBlockEntity.this.progress = value; break;
                    case 1: AlloyBlasterBlockEntity.this.maxProgress = value; break;
                    case 2: AlloyBlasterBlockEntity.this.fuelTime = value; break;
                    case 3: AlloyBlasterBlockEntity.this.maxFuelTime = value; break;
                }
            }

            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("alloy_blaster_display_name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new AlloyBlasterMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("blaster.progress", progress);
        tag.putInt("blaster.fuelTime", fuelTime);
        tag.putInt("blaster.maxFuelTime", maxFuelTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("blaster.progress");
        fuelTime = nbt.getInt("blaster.fuelTime");
        maxFuelTime = nbt.getInt("blaster.maxFuelTime");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    private void consumeFuel() {
        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            this.fuelTime = ForgeHooks.getBurnTime(this.itemHandler.extractItem(0, 1, false),
                    RecipeType.SMELTING);
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AlloyBlasterBlockEntity pBlockEntity) {
        if(isConsumingFuel(pBlockEntity)) {
            pBlockEntity.fuelTime--;
        }

        if(hasThreeIngredientRecipe(pBlockEntity)) {
            if(hasFuelInFuelSlot(pBlockEntity) && !isConsumingFuel(pBlockEntity)) {
                pBlockEntity.consumeFuel();
                setChanged(pLevel, pPos, pState);
            }
            if(isConsumingFuel(pBlockEntity)) {
                pBlockEntity.progress++;
                setChanged(pLevel, pPos, pState);
                if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                    craftTripleRecipe(pBlockEntity);
                }
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasFuelInFuelSlot(AlloyBlasterBlockEntity entity) {
        return !entity.itemHandler.getStackInSlot(0).isEmpty();
    }

    private static boolean isConsumingFuel(AlloyBlasterBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasThreeIngredientRecipe(AlloyBlasterBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        inventory.setItem(0, entity.itemHandler.getStackInSlot(0));
        inventory.setItem(1, entity.itemHandler.getStackInSlot(1));
        inventory.setItem(2, entity.itemHandler.getStackInSlot(2));
        inventory.setItem(3, entity.itemHandler.getStackInSlot(3));
        inventory.setItem(4, entity.itemHandler.getStackInSlot(4));

        Optional<AlloyBlasterRecipe> match = level.getRecipeManager()
                .getRecipeFor(AlloyBlasterRecipe.Type.INSTANCE, inventory, level);
        return
                match.isPresent() //is there even a recipe at all?
                &&
                inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + match.get().getOutputCount() //can the amount of the item fit?
                &&
                (inventory.getItem(4).getItem() == match.get().getResultItem().getItem() || inventory.getItem(4).isEmpty()) // does the result item match the item sitting in the output? OR is the output empty?
                &&
                entity.itemHandler.getStackInSlot(1).getCount() >= match.get().getIng1count() // does slot 1 contain enough items?
                &&
                entity.itemHandler.getStackInSlot(2).getCount() >= match.get().getIng2count() // does slot 2 contain enough items?
                &&
                entity.itemHandler.getStackInSlot(3).getCount() >= match.get().getIng3count() // does slot 3 contain enough items?
                ;
    }
    private static boolean hasTwoIngredientRecipe(AlloyBlasterBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        inventory.setItem(0, entity.itemHandler.getStackInSlot(0));
        inventory.setItem(1, entity.itemHandler.getStackInSlot(1));
        inventory.setItem(2, entity.itemHandler.getStackInSlot(2));
        inventory.setItem(3, entity.itemHandler.getStackInSlot(3));
        inventory.setItem(4, entity.itemHandler.getStackInSlot(4));

        Optional<AlloyBlasterRecipe> match = level.getRecipeManager()
                .getRecipeFor(AlloyBlasterRecipe.Type.INSTANCE, inventory, level);
        return
                match.isPresent() //is there even a recipe at all?
                        &&
                        inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + match.get().getOutputCount() //can the amount of the item fit?
                        &&
                        (inventory.getItem(4).getItem() == match.get().getResultItem().getItem() || inventory.getItem(4).isEmpty()) // does the result item match the item sitting in the output? OR is the output empty?
                        &&
                        entity.itemHandler.getStackInSlot(1).getCount() >= match.get().getIng1count() // does slot 1 contain enough items?
                        &&
                        entity.itemHandler.getStackInSlot(2).getCount() >= match.get().getIng2count() // does slot 2 contain enough items?
                        &&
                        entity.itemHandler.getStackInSlot(3).getCount() >= match.get().getIng3count() // does slot 3 contain enough items?
                ;
    }

    private static void craftTripleRecipe(AlloyBlasterBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlloyBlasterRecipe> match = level.getRecipeManager()
                .getRecipeFor(AlloyBlasterRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
           // if (entity.itemHandler.getStackInSlot(1).getCount() >= match.get().getIng1count() && entity.itemHandler.getStackInSlot(2).getCount() >= match.get().getIng2count()) {
                entity.itemHandler.extractItem(1, match.get().getIng1count(), false);
                entity.itemHandler.extractItem(2, match.get().getIng2count(), false);
                entity.itemHandler.extractItem(3, match.get().getIng3count(), false);
                entity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItem().getItem(),
                        entity.itemHandler.getStackInSlot(4).getCount() + match.get().getOutputCount()));

                entity.resetProgress();
         //   }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

}
