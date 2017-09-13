package zachy.cosmic.integration.jei.chemical_reactor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import zachy.cosmic.core.Lib;

public class RecipeCategoryChemicalReactor implements IRecipeCategory<RecipeWrapperChemicalReactor> {

    public static final String ID = "cosmic.chemical_reactor";

    private IDrawable background;

    public RecipeCategoryChemicalReactor(IGuiHelper helper) {
        background = helper.createDrawable(new ResourceLocation(Lib.MOD_ID, "textures/gui/chemical_reactor.png"), 70, 23, 36, 54);
    }

    @Override
    public String getUid() {
        return ID;
    }

    @Override
    public String getTitle() {
        return I18n.format("gui.cosmic:chemical_reactor");
    }

    @Override
    public String getModName() {
        return I18n.format("itemGroup.cosmic");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperChemicalReactor recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();

        group.init(0, true, 0, 0);
        group.init(1, true, 18, 0);
        group.init(2, false, 9, 36);

        group.set(0, ingredients.getInputs(ItemStack.class).get(0));
        group.set(1, ingredients.getInputs(ItemStack.class).get(1));
        group.set(2, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
