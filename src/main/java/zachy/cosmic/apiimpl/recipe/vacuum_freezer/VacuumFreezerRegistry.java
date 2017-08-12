package zachy.cosmic.apiimpl.recipe.vacuum_freezer;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.api.recipe.vacuum_freezer.IVacuumFreezerRecipe;
import zachy.cosmic.api.recipe.vacuum_freezer.IVacuumFreezerRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.API;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class VacuumFreezerRegistry implements IVacuumFreezerRegistry {

    List<IVacuumFreezerRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(@Nonnull IVacuumFreezerRecipe recipe) {
        recipes.add(recipe);
    }

    @Nullable
    @Override
    public IVacuumFreezerRecipe getRecipe(IInventory inventory) {
        for (IVacuumFreezerRecipe recipe : recipes) {
            NonNullList<ItemStack> possibilities = recipe.getInput();

            for (ItemStack possibility : possibilities) {
                if (API.instance().getComparer().isEqual(possibility, inventory.getStackInSlot(0), IComparer.COMPARE_NBT | IComparer.COMPARE_DAMAGE | IComparer.COMPARE_STRIP_NBT)) {
                    return recipe;
                }
            }
        }

        return null;
    }

    @Override
    public List<IVacuumFreezerRecipe> getRecipes() {
        return recipes;
    }
}
