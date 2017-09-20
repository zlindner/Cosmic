package zachy.cosmic.integration.jei;

import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.apiimpl.API;

import java.util.ArrayList;
import java.util.List;

public class RecipeMakerMachine {

    public static List<RecipeWrapperMachine> getRecipes(JEIMachine m, IGuiHelper helper) {
        List<RecipeWrapperMachine> recipes = new ArrayList<>();

        for (IMachineRecipe recipe : API.instance().getMachineRegistry(m.getName()).getRecipes()) {
            List<ItemStack> inputs = new ArrayList<>();

            for (int i = 0; i < m.getInputs(); i++) {
                inputs.add(i, recipe.getInput(i));
            }

            List<ItemStack> outputs = new ArrayList<>();

            for (int i = 0; i < m.getOutputs(); i++) {
                outputs.add(i, recipe.getOutput(i));
            }

            int duration = recipe.getDuration();
            int energy = recipe.getEnergy();
            int heat = recipe.getHeat();
            FluidStack fluid = recipe.getFluid();
            int fluid_amount = recipe.getFluidAmount();

            recipes.add(new RecipeWrapperMachine(m, inputs, outputs, duration, energy, heat, fluid, fluid_amount, helper));
        }

        return recipes;
    }
}
