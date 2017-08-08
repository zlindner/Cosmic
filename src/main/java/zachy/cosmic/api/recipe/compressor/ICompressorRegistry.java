package zachy.cosmic.api.recipe.compressor;

import net.minecraft.inventory.IInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The recipe registry of the compressor.
 */
public interface ICompressorRegistry {

    /**
     * Adds a recipe to the registry.
     *
     * @param recipe the recipe to add
     */
    void addRecipe(@Nonnull ICompressorRecipe recipe);

    /**
     * Returns a compressor recipe from the slots.
     *
     * @param inventory an iinventory, where slots 0 and 1 are the inputs, 2 and 3 are the outputs
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    ICompressorRecipe getRecipe(IInventory inventory);

    /**
     * @return a list with all of the compressor recipes
     */
    List<ICompressorRecipe> getRecipes();
}

