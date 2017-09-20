package zachy.cosmic.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import zachy.cosmic.core.helper.JEIHelper;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();

        for (JEIMachine m : JEIMachine.values()) {
            String name = m.getName();
            String ID = name;

            registry.getRecipeTransferRegistry().addRecipeTransferHandler(JEIHelper.getContainer(name), ID, 0, m.getInputs(), 0, 36);
            registry.handleRecipes(RecipeWrapperMachine.class, recipe -> recipe, ID);
            registry.addRecipes(RecipeMakerMachine.getRecipes(m, helper), ID);
            registry.addRecipeCatalyst(JEIHelper.getCatalyst(name), ID);
            registry.addRecipeClickArea(JEIHelper.getGui(name), 0, 0, 0, 0, ID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();

        for (JEIMachine m : JEIMachine.values()) {
            System.out.println(m.getName());
            registry.addRecipeCategories(new RecipeCategoryMachine(m, helper));
        }
    }
}
