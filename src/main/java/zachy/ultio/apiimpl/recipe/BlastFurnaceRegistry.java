package zachy.ultio.apiimpl.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.ultio.api.recipe.IBlastFurnaceRecipe;
import zachy.ultio.api.recipe.IBlastFurnaceRegistry;
import zachy.ultio.api.util.IComparer;
import zachy.ultio.apiimpl.API;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class BlastFurnaceRegistry implements IBlastFurnaceRegistry {

    private List<IBlastFurnaceRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(@Nonnull IBlastFurnaceRecipe recipe) {
        recipes.add(recipe);
    }

    @Nullable
    @Override
    public IBlastFurnaceRecipe getRecipe(IInventory inventory) {
        for (IBlastFurnaceRecipe recipe : recipes) {
            int inputsFound = 0;

            for (int i = 0; i < 2; i++) {
                NonNullList<ItemStack> possibilities = recipe.getInput(i);

                if (possibilities.isEmpty() && inventory.getStackInSlot(i).isEmpty()) {
                    inputsFound++;

                    continue;
                }

                for (ItemStack possibility : possibilities) {
                    if (API.instance().getComparer().isEqual(possibility, inventory.getStackInSlot(i), IComparer.COMPARE_NBT | IComparer.COMPARE_DAMAGE | IComparer.COMPARE_STRIP_NBT)) {
                        if (inventory.getStackInSlot(i).getCount() >= possibility.getCount()) {
                            inputsFound++;

                            break;
                        }
                    }
                }
            }

            if (inputsFound == 2) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    public List<IBlastFurnaceRecipe> getRecipes() {
        return recipes;
    }
}
