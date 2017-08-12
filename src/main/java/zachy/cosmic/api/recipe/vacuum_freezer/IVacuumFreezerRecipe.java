package zachy.cosmic.api.recipe.vacuum_freezer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Represents a recipe in the vacuum freezer.
 */
public interface IVacuumFreezerRecipe {

    /**
     * @return the name of this vacuum freezer recipe
     */
    ResourceLocation getName();

    /**
     * @return possible stack(s) for the input slot, or empty list for no stack
     */
    @Nonnull
    NonNullList<ItemStack> getInput();

    /**
     * @return the stack returned by the recipe
     */
    @Nonnull
    ItemStack getOutput();

    /**
     * @return the duration in ticks that this recipe requires to complete
     */
    int getDuration();

    /**
     * @return the energy in eu per tick that this recipe requires to complete
     */
    int getEnergy();
}
