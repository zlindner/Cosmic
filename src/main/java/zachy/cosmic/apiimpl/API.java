package zachy.cosmic.apiimpl;

import zachy.cosmic.api.IAPI;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.apiimpl.recipe.MachineRegistry;
import zachy.cosmic.core.Lib;

public class API implements IAPI {

    private static final IAPI INSTANCE = new API();

    private final IMachineRegistry blastFurnaceRegistry = new MachineRegistry();
    private final IMachineRegistry compressorRegistry = new MachineRegistry();
    private final IMachineRegistry distillationTowerRegistry = new MachineRegistry();
    private final IMachineRegistry electrolyzerRegistry = new MachineRegistry();
    private final IMachineRegistry grinderRegistry = new MachineRegistry();
    private final IMachineRegistry sawmillRegistry = new MachineRegistry();
    private final IMachineRegistry vacuumFreezerRegistry = new MachineRegistry();

    public static IAPI instance() {
        return INSTANCE;
    }

    @Override
    public IMachineRegistry getMachineRegistry(String machine) {
        switch (machine) {
            case Lib.Blocks.BLAST_FURNACE:
                return blastFurnaceRegistry;
            case Lib.Blocks.COMPRESSOR:
                return compressorRegistry;
            case Lib.Blocks.DISTILLATION_TOWER:
                return distillationTowerRegistry;
            case Lib.Blocks.ELECTROLYZER:
                return electrolyzerRegistry;
            case Lib.Blocks.GRINDER:
                return grinderRegistry;
            case Lib.Blocks.SAWMILL:
                return sawmillRegistry;
            case Lib.Blocks.VACUUM_FREEZER:
                return vacuumFreezerRegistry;
        }

        return null;
    }
}
