package zachy.cosmic.apiimpl.recipe.compressor;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.api.recipe.compressor.ICompressorRecipe;
import zachy.cosmic.api.recipe.compressor.ICompressorRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.API;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class CompressorRegistry implements ICompressorRegistry {

    private List<ICompressorRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(@Nonnull ICompressorRecipe recipe) {
        recipes.add(recipe);
    }

    @Nullable
    @Override
    public ICompressorRecipe getRecipe(IInventory inventory) {
        for (ICompressorRecipe recipe : recipes) {
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
    public List<ICompressorRecipe> getRecipes() {
        return recipes;
    }
}
