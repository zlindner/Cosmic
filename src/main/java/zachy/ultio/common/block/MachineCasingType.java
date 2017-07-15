package zachy.ultio.common.block;

import net.minecraft.util.IStringSerializable;

public enum MachineCasingType implements IStringSerializable {
    STANDARD,
    REINFORCED,
    ADVANCED;

    public int getId() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
