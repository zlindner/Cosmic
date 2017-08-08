package zachy.cosmic.api.recipe.compressor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Represents a recipe in the compressor.
 */
public interface ICompressorRecipe {

    /**
     * @return the name of this compressor recipe
     */
    ResourceLocation getName();

    /**
     * @param index the input slot that we want the stack for (0 - 1)
     * @return possible stack(s) for the given slot, or empty list for no stack
     */
    @Nonnull
    NonNullList<ItemStack> getInput(int index);

    /**
     * @param index the output slot that we want the stack for (0 - 1)
     * @return the stack returned by the recipe for the given slot
     */
    @Nonnull
    ItemStack getOutput(int index);

    /**
     * @return the duration in ticks that this recipe requires to complete
     */
    int getDuration();

    /**
     * @return the energy in eu per tick that this recipe requires to complete
     */
    int getEnergy();
}
