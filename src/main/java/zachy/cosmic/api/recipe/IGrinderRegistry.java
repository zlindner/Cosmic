package zachy.cosmic.api.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The recipe registry of the industrial grinder.
 */
public interface IGrinderRegistry {

    /**
     * Adds a recipe to the registry.
     *
     * @param recipe the recipe to add
     */
    void addRecipe(@Nonnull IGrinderRecipe recipe);

    /**
     * Returns an industrial grinder recipe from the slots.
     *
     * @param inventory an iinventory, where slot 0 is the inputs, 1, 2, 3 and 4 are the outputs
     * @param tank a fluid tank
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    IGrinderRecipe getRecipe(IInventory inventory, FluidTank tank);

    /**
     * @return a list with all of the industrial grinder recipes
     */
    List<IGrinderRecipe> getRecipes();
}
