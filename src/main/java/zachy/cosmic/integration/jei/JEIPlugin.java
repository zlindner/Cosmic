package zachy.cosmic.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import zachy.cosmic.client.gui.machine.GuiChemicalReactor;
import zachy.cosmic.container.machine.ContainerChemicalReactor;
import zachy.cosmic.core.init.ModBlocks;
import zachy.cosmic.integration.jei.chemical_reactor.RecipeCategoryChemicalReactor;
import zachy.cosmic.integration.jei.chemical_reactor.RecipeMakerChemicalReactor;
import zachy.cosmic.integration.jei.chemical_reactor.RecipeWrapperChemicalReactor;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerChemicalReactor.class, RecipeCategoryChemicalReactor.ID, 0, 2, 0, 36);

        registry.handleRecipes(RecipeWrapperChemicalReactor.class, recipe -> recipe, RecipeCategoryChemicalReactor.ID);

        registry.addRecipes(RecipeMakerChemicalReactor.getRecipes(guiHelper), RecipeCategoryChemicalReactor.ID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.chemical_reactor), RecipeCategoryChemicalReactor.ID);

        registry.addRecipeClickArea(GuiChemicalReactor.class, 73, 45, 30, 10, RecipeCategoryChemicalReactor.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new RecipeCategoryChemicalReactor(guiHelper));
    }
}
