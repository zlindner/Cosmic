package zachy.cosmic.client.gui;

// todo remove and migrate to using MachineType
public enum Guis {
    BLAST_FURNACE,
    CENTRIFUGE,
    CHEMICAL_REACTOR,
    COMPRESSOR,
    DISTILLATION_TOWER,
    ELECTROLYZER,
    GRINDER,
    SAWMILL,
    VACUUM_FREEZER;

    public int ID() {
        return ordinal();
    }
}
