package zachy.cosmic.core;

public final class Lib {
    public static final String MOD_ID = "cosmic";
    public static final String MOD_NAME = "Cosmic";
    public static final String VERSION = "0.0.1";

    public static final String PROXY_SERVER = "zachy.cosmic.core.proxy.ServerProxy";
    public static final String PROXY_CLIENT = "zachy.cosmic.core.proxy.ClientProxy";

    public static final class Blocks {
        public static final String BLAST_FURNACE = "blast_furnace";
        public static final String CENTRIFUGE = "centrifuge";
        public static final String CHEMICAL_REACTOR = "chemical_reactor";
        public static final String COMPRESSOR = "compressor";
        public static final String DISTILLATION_TOWER = "distillation_tower";
        public static final String ELECTROLYZER = "electrolyzer";
        public static final String GRINDER = "grinder";
        public static final String MACHINE_CASING = "machine_casing";
        public static final String MACHINE_FRAME = "machine_frame";
        public static final String SAWMILL = "sawmill";
        public static final String VACUUM_FREEZER = "vacuum_freezer";
    }

    public static final class Items {
        public static final String CELL = "cell";
        public static final String CELL_TYPES[] = {
                "berylium",
                "bio_diesel",
                "biomass",
                "calcium",
                "calcium_carbonate",
                "carbon",
                "chlorite",
                "compressed_air",
                "deuterium",
                "diesel",
                "glyceryl",
                "helium",
                "helium-3",
                "helium_plasma",
                "hydrogen",
                "ice",
                "lithium",
                "mercury",
                "methane",
                "nitro_carbon",
                "nitro_coalfuel",
                "nitro_diesel",
                "nitrogen",
                "nitrogen_dioxide",
                "oil",
                "potassium",
                "seed_oil",
                "silicon",
                "sodium",
                "sodium_persulfate",
                "sodium_sulfide",
                "sulfur",
                "sulfuric_acid",
                "tritium",
                "wolframium",
                "60k_helium_coolant",
                "180k_helium_coolant",
                "360k_helium_coolant",
                "60k_nak_coolant",
                "180k_nak_coolant",
                "360k_nak_coolant"
        };
        public static final String PORTABLE_SCANNER = "portable_scanner";
    }

    public static final class NBT {
        public static final String ACTIVE = "Active";
        public static final String DIRECTION = "Direction";
        public static final String ENERGY = "Energy";
        public static final String HEAT = "Heat";
        public static final String INVENTORY = "Inventory";
        public static final String PROGRESS = "Progress";
        public static final String SLOT = "Slot";
        public static final String VALID = "Valid";
    }
}
