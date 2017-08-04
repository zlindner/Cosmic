package zachy.cosmic.api.recipe.blast_furnace;

import net.minecraft.inventory.IInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The recipe registry of the industrial blast furnace.
 */
public interface IBlastFurnaceRegistry {

    /**
     * Adds a recipe to the registry.
     *
     * @param recipe the recipe to add
     */
    void addRecipe(@Nonnull IBlastFurnaceRecipe recipe);

    /**
     * Returns an industrial blast furnace recipe from the slots.
     *
     * @param inventory an iinventory, where slots 0 and 1 are the inputs, 2 and 3 are the outputs
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    IBlastFurnaceRecipe getRecipe(IInventory inventory);

    /**
     * @return a list with all of the industrial blast furnace recipes
     */
    List<IBlastFurnaceRecipe> getRecipes();
}
