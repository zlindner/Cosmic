package zachy.cosmic.apiimpl.state.enums;

public enum MachineType {
    BLAST_FURNACE(2, 2),
    COMPRESSOR(2, 2),
    DISTILLATION_TOWER(2, 3),
    ELECTROLYZER(2, 4),
    GRINDER(2, 4),
    SAWMILL(1, 2),
    VACUUM_FREEZER(1, 1);

    private int inputs;
    private int outputs;

    MachineType(int inputs, int outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getInputs() {
        return inputs;
    }

    public int getOutputs() {
        return outputs;
    }
}
