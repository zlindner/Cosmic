package zachy.cosmic.client.gui;

public enum Guis {
    BLAST_FURNACE,
    CENTRIFUGE,
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
