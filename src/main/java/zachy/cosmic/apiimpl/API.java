package zachy.cosmic.apiimpl;

import zachy.cosmic.api.IAPI;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.apiimpl.recipe.MachineRegistry;
import zachy.cosmic.core.Lib;

public class API implements IAPI {

    private static final IAPI INSTANCE = new API();

    private final IMachineRegistry blast_furnace_registry = new MachineRegistry();
    private final IMachineRegistry centrifuge_registry = new MachineRegistry();
    private final IMachineRegistry chemical_reactor_registry = new MachineRegistry();
    private final IMachineRegistry compressor_registry = new MachineRegistry();
    private final IMachineRegistry distillation_tower_registry = new MachineRegistry();
    private final IMachineRegistry electrolyzer_registry = new MachineRegistry();
    private final IMachineRegistry grinder_registry = new MachineRegistry();
    private final IMachineRegistry sawmill_registry = new MachineRegistry();
    private final IMachineRegistry vacuum_freezer_registry = new MachineRegistry();

    public static IAPI instance() {
        return INSTANCE;
    }

    @Override
    public IMachineRegistry getMachineRegistry(String machine) {
        switch (machine) {
            case Lib.Blocks.BLAST_FURNACE:
                return blast_furnace_registry;
            case Lib.Blocks.CENTRIFUGE:
                return centrifuge_registry;
            case Lib.Blocks.CHEMICAL_REACTOR:
                return chemical_reactor_registry;
            case Lib.Blocks.COMPRESSOR:
                return compressor_registry;
            case Lib.Blocks.DISTILLATION_TOWER:
                return distillation_tower_registry;
            case Lib.Blocks.ELECTROLYZER:
                return electrolyzer_registry;
            case Lib.Blocks.GRINDER:
                return grinder_registry;
            case Lib.Blocks.SAWMILL:
                return sawmill_registry;
            case Lib.Blocks.VACUUM_FREEZER:
                return vacuum_freezer_registry;
        }

        return null;
    }
}
