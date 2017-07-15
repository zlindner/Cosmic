package zachy.ultio.common.block;

import net.minecraft.util.IStringSerializable;

public enum CableType implements IStringSerializable {
    COPPER,
    TIN,
    GOLD;

    public int getId() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
