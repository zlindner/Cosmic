package zachy.cosmic.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

/**
 * Represents a recipe in a machine
 */
public interface IMachineRecipe {

    /**
     * @return the name of the recipe
     */
    ResourceLocation getName();

    /**
     * @param index the slot we want the stack from
     *
     * @return stack required for the given slot
     */
    ItemStack getInput(int index);

    /**
     * @param index the slot we want the stack from
     *
     * @return stack returned by the recipe for the given slot
     */
    ItemStack getOutput(int index);

    /**
     * @return number of ticks required by the recipe
     */
    int getDuration();

    /**
     * @return amount of energy required by the recipe (EU/t)
     */
    int getEnergy();

    /**
     * @return the fluid required by the recipe
     */
    FluidStack getFluid();

    /**
     * @return the amount of fluid required by the recipe
     */
    int getFluidAmount();

    /**
     * @return temperature required by the recipe
     */
    int getHeat();
}

