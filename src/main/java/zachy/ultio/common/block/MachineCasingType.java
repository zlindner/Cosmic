package zachy.ultio.common.block;

import net.minecraft.util.IStringSerializable;

public enum MachineCasingType implements IStringSerializable {
    STANDARD,
    REINFORCED,
    ADVANCED;

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
