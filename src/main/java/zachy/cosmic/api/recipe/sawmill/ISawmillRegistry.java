package zachy.cosmic.api.recipe.sawmill;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The recipe registry of the sawmill.
 */
public interface ISawmillRegistry {

    /**
     * Adds a recipe to the registry.
     *
     * @param recipe the recipe to add
     */
    void addRecipe(@Nonnull ISawmillRecipe recipe);

    /**
     * Returns an sawmill recipe from the slots.
     *
     * @param inventory an iinventory, where slot 0 is the inputs, 1, and 2 are the outputs
     * @param tank a fluid tank
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    ISawmillRecipe getRecipe(IInventory inventory, FluidTank tank);

    /**
     * @return a list with all of the sawmill recipes
     */
    List<ISawmillRecipe> getRecipes();
}
