package zachy.cosmic.apiimpl.state.enums;

import net.minecraft.util.IStringSerializable;

public enum MachineFrameType implements IStringSerializable {
    BASIC,
    INTERMEDIATE,
    ADVANCED;

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}