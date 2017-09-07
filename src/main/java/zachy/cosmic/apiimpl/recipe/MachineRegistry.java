package zachy.cosmic.apiimpl.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.core.util.StackUtils;

import java.util.LinkedList;
import java.util.List;

public class MachineRegistry implements IMachineRegistry {

    private List<IMachineRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(IMachineRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public IMachineRecipe getRecipe(IInventory inventory, int inputs) {
        for (IMachineRecipe recipe : recipes) {
            int inputsFound = 0;

            for (int i = 0; i < inputs; i++) {
                ItemStack recipeInput = recipe.getInput(i);
                ItemStack inputStack = inventory.getStackInSlot(i);

                if (recipeInput.isEmpty() && inputStack.isEmpty()) {
                    inputsFound++;

                    continue;
                }

                if (StackUtils.isCraftingEquivalent(recipeInput, inputStack, true)) {
                    if (inputStack.getCount() >= recipeInput.getCount()) {
                        inputsFound++;
                    }
                }

            }

            if (inputsFound == inputs) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    public IMachineRecipe getRecipe(IInventory inventory, int inputs, FluidTank tank) {
        for (IMachineRecipe recipe : recipes) {
            int inputsFound = 0;

            for (int i = 0; i < inputs; i++) {
                ItemStack recipeInput = recipe.getInput(i);
                ItemStack inputStack = inventory.getStackInSlot(i);

                if (recipeInput.isEmpty() && inputStack.isEmpty()) {
                    inputsFound++;

                    continue;
                }

                if (StackUtils.isCraftingEquivalent(recipeInput, inputStack, true)) {
                    if (inputStack.getCount() >= recipeInput.getCount()) {
                        inputsFound++;
                    }
                }
            }

            if (inputsFound != inputs) {
                return null;
            }

            FluidStack fluid = recipe.getFluid();

            if ((fluid == null && tank.getFluid() == null) || fluid.isFluidEqual(tank.getFluid())) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    public List<IMachineRecipe> getRecipes() {
        return recipes;
    }
}
