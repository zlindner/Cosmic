package zachy.cosmic.apiimpl.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.API;

import java.util.LinkedList;
import java.util.List;

public class MachineRegistry implements IMachineRegistry {

    private List<IMachineRecipe> recipes = new LinkedList<>();

    @Override
    public void addRecipe(IMachineRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public IMachineRecipe getRecipe(IInventory inventory, int inputs) {
        for (IMachineRecipe recipe : recipes) {
            int inputsFound = 0;

            for (int i = 0; i < inputs; i++) {
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

            if (inputsFound == inputs) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    public List<IMachineRecipe> getRecipes() {
        return recipes;
    }
}
