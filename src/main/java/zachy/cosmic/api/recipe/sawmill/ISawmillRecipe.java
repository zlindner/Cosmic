package zachy.cosmic.api.recipe.sawmill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/**
 * Represents a recipe in the sawmill.
 */
public interface ISawmillRecipe {

    /**
     * @return the name of this sawmill recipe
     */
    ResourceLocation getName();

    /**
     * @return possible stack(s) for the input slot, or empty list for no stack
     */
    @Nonnull
    NonNullList<ItemStack> getInput();

    /**
     * @return the fluidstack required by the recipe (if needed)
     */
    FluidStack getFluid();

    /**
     * @return the amount of fluid required by the recipe (if needed)
     */
    int getFluidAmount();

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
