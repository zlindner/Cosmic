package zachy.cosmic.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class RecipeWrapperMachine implements IRecipeWrapper {

    private List<ItemStack> inputs;
    private List<ItemStack> outputs;
    private int duration;
    private int energy;
    private int heat;
    private FluidStack fluid;
    private int fluid_amount;

    private IDrawableAnimated progress;

    public RecipeWrapperMachine(JEIMachine m, List<ItemStack> inputs, List<ItemStack> outputs, int duration, int energy, int heat, FluidStack fluid, int fluid_amount, IGuiHelper helper) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.duration = duration;
        this.energy = energy;
        this.heat = heat;
        this.fluid = fluid;
        this.fluid_amount = fluid_amount;

        progress = helper.createAnimatedDrawable(helper.createDrawable(m.getResourceLocation(), m.getProgressBound(0),  m.getProgressBound(1),  m.getProgressBound(2),  m.getProgressBound(3)), 100, m.getDirection(), false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        progress.draw(minecraft, 3, 22);
    }
}
