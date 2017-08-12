package zachy.cosmic.api.recipe.vacuum_freezer;

import net.minecraft.inventory.IInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The recipe registry of the vacuum freezer.
 */
public interface IVacuumFreezerRegistry {

    /**
     * Adds a recipe to the registry.
     *
     * @param recipe the recipe to add
     */
    void addRecipe(@Nonnull IVacuumFreezerRecipe recipe);

    /**
     * Returns a vacuum freezer recipe from the slots.
     *
     * @param inventory an iinventory, where slot 0 is the input, 2 is the output
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    IVacuumFreezerRecipe getRecipe(IInventory inventory);

    /**
     * @return a list with all of the vacuum freezer recipes
     */
    List<IVacuumFreezerRecipe> getRecipes();
}
