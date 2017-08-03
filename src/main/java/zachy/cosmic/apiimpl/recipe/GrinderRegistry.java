package zachy.cosmic.apiimpl.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import zachy.cosmic.api.recipe.IGrinderRecipe;
import zachy.cosmic.api.recipe.IGrinderRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.API;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class GrinderRegistry implements IGrinderRegistry {

    private List<IGrinderRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(@Nonnull IGrinderRecipe recipe) {
        recipes.add(recipe);
    }

    @Nullable
    @Override
    public IGrinderRecipe getRecipe(IInventory inventory, FluidTank tank) {
        for (IGrinderRecipe recipe : recipes) {
            boolean itemFound = false;
            boolean fluidFound = false;

            NonNullList<ItemStack> possibilities = recipe.getInput();

            if (possibilities.isEmpty() && inventory.getStackInSlot(0).isEmpty()) {
                itemFound = true;
            }

            FluidStack fluid = recipe.getFluid();

            if (fluid == null && tank.getFluid() == null) {
                fluidFound = true;
            }

            if (itemFound && fluidFound) {
                return recipe;
            }

            for (ItemStack possibility : possibilities) {
                if (API.instance().getComparer().isEqual(possibility, inventory.getStackInSlot(0), IComparer.COMPARE_NBT | IComparer.COMPARE_DAMAGE | IComparer.COMPARE_STRIP_NBT)) {
                    if (inventory.getStackInSlot(0).getCount() >= possibility.getCount()) {
                        itemFound = true;

                        break;
                    }
                }
            }

            if (fluid.isFluidEqual(tank.getFluid())) {
                fluidFound = true;
            }

            if (itemFound && fluidFound) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    public List<IGrinderRecipe> getRecipes() {
        return recipes;
    }
}
