package zachy.cosmic.api;

import zachy.cosmic.api.recipe.IMachineRegistry;

/**
 * Represents a Cosmic API implementation
 * Delivered by the {@link IInjector} annotation
 */
public interface IAPI {

    /**
     * @param machine the name of the machine
     * @return the machine registry for the given machine
     */
    IMachineRegistry getMachineRegistry(String machine);
}
