package zachy.ultio.api;

import net.minecraft.item.ItemStack;
import zachy.ultio.api.recipe.IBlastFurnaceRegistry;
import zachy.ultio.api.util.IComparer;

import javax.annotation.Nonnull;

/**
 * Represents a Ultio API implementation.
 * Delivered by the {@link IInjector} annotation.
 */
public interface IAPI {

    /**
     * @return the comparer
     */
    @Nonnull
    IComparer getComparer();

    /**
     * @return the blast furnace registry
     */
    @Nonnull
    IBlastFurnaceRegistry getBlastFurnaceRegistry();

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
