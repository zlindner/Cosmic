package zachy.cosmic.integration.jei.chemical_reactor;

import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.core.Lib;

import java.util.ArrayList;
import java.util.List;

public class RecipeMakerChemicalReactor {

    public static List<RecipeWrapperChemicalReactor> getRecipes(IGuiHelper guiHelper) {
        List<RecipeWrapperChemicalReactor> recipes = new ArrayList<>();

        for (IMachineRecipe recipe : API.instance().getMachineRegistry(Lib.Blocks.CHEMICAL_REACTOR).getRecipes()) {
            List<ItemStack> inputs = new ArrayList<>();

            inputs.add(recipe.getInput(0));
            inputs.add(recipe.getInput(1));

            ItemStack output = recipe.getOutput(0);

            recipes.add(new RecipeWrapperChemicalReactor(guiHelper, inputs, output, recipe.getDuration(), recipe.getEnergy()));
        }

        return recipes;
    }
}
