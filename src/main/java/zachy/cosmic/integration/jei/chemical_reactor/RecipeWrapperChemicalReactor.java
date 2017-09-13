package zachy.cosmic.integration.jei.chemical_reactor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import zachy.cosmic.core.Lib;

import java.util.List;

public class RecipeWrapperChemicalReactor implements IRecipeWrapper {

    private List<ItemStack> inputs;
    private ItemStack output;
    private int duration;
    private int energy;

    private IDrawableAnimated progress;

    public RecipeWrapperChemicalReactor(IGuiHelper guiHelper, List<ItemStack> inputs, ItemStack output, int duration, int energy) {
        this.inputs = inputs;
        this.output = output;
        this.duration = duration;
        this.energy = energy;

        progress = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(new ResourceLocation(Lib.MOD_ID, "textures/gui/chemical_reactor.png"), 176, 0, 39, 9), duration, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        progress.draw(minecraft, 3, 22);

        // fix drawing this if showing usages
        String info[] = {
                "Duration: " + String.valueOf(duration / 20) + " seconds",
                "Energy Per Tick: " + String.valueOf(energy),
                "Total Energy: " + String.valueOf(energy * duration)
        };

        for (int i = 0; i < info.length; i++) {
            minecraft.fontRenderer.drawString(info[i], recipeWidth / 2 - minecraft.fontRenderer.getStringWidth(info[i]) / 2, recipeHeight + 20 + 10 * i, 4210752);
        }
    }
}
