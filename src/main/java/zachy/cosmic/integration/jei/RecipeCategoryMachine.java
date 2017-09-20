package zachy.cosmic.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class RecipeCategoryMachine implements IRecipeCategory<RecipeWrapperMachine> {

    JEIMachine m;

    private IDrawable background;

    public RecipeCategoryMachine(JEIMachine m, IGuiHelper helper) {
        this.m = m;

        background = helper.createDrawable(m.getResourceLocation(), m.getBackgroundBound(0), m.getBackgroundBound(1), m.getBackgroundBound(2), m.getBackgroundBound(3));
    }

    @Override
    public String getUid() {
        return m.getName();
    }

    @Override
    public String getTitle() {
        return I18n.format("gui.cosmic:" + m.getName());
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
    public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperMachine recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();

        for (int i = 0; i < m.getInputs(); i++) {
            group.init(i, true, m.getIngredientX(i), m.getIngredientY(i));
            group.set(i, ingredients.getInputs(ItemStack.class).get(i));
        }

        for (int i = m.getInputs(); i < m.getInputs() + m.getOutputs(); i++) {
            group.init(i, false, m.getIngredientX(i), m.getIngredientY(i));
            group.set(i, ingredients.getOutputs(ItemStack.class).get(i - m.getInputs()));
        }
    }
}
