package zachy.cosmic.api;

import net.minecraft.item.ItemStack;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.api.util.IComparer;

import javax.annotation.Nonnull;

/**
 * Represents a Cosmic API implementation.
 * Delivered by the {@link IInjector} annotation.
 */
public interface IAPI {

    //TODO remove, add to stackutils
    /**
     * @return the comparer
     */
    @Nonnull
    IComparer getComparer();

    /**
     * @param machine the name of the machine
     * @return the machine registry for the given machine
     */
    IMachineRegistry getMachineRegistry(String machine);

    //TODO stahp this
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
