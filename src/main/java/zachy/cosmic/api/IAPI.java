package zachy.cosmic.api;

import net.minecraft.item.ItemStack;
import zachy.cosmic.api.recipe.blast_furnace.IBlastFurnaceRegistry;
import zachy.cosmic.api.recipe.grinder.IGrinderRegistry;
import zachy.cosmic.api.util.IComparer;

import javax.annotation.Nonnull;

/**
 * Represents a Cosmic API implementation.
 * Delivered by the {@link IInjector} annotation.
 */
public interface IAPI {

    /**
     * @return the comparer
     */
    @Nonnull
    IComparer getComparer();

    /**
     * @return the industrial blast furnace registry
     */
    @Nonnull
    IBlastFurnaceRegistry getBlastFurnaceRegistry();

    /**
     * @return the industrial grinder registry
     */
    @Nonnull
    IGrinderRegistry getGrinderRegistry();

    /**
     * @param stack the stack
     * @param tag   whether the NBT tag of the stack should be calculated in the hashcode, used for performance reasons
     * @return a hashcode for the given stack
     */
    int getItemStackHashCode(ItemStack stack, boolean tag);

    /**
     * @param stack the stack
     * @return a hashcode for the given stack
     */
    default int getItemStackHashCode(ItemStack stack) {
        return getItemStackHashCode(stack, true);
    }
}
