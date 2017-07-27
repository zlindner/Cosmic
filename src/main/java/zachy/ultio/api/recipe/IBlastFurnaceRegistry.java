package zachy.ultio.api.recipe;

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
     * Returns a industrial blast furnace recipe from the slots.
     *
     * @param inventory an item handler, where slots 0 and 1 are the inputs
     * @return the recipe, or null if no recipe was found
     */
    @Nullable
    IBlastFurnaceRecipe getRecipe(IInventory inventory);

    /**
     * @return a list with all the industrial blast furnace recipes
     */
    List<IBlastFurnaceRecipe> getRecipes();
}
