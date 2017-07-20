package zachy.ultio.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileBase extends TileEntity {

    private EnumFacing direction = EnumFacing.NORTH;

    public void setDirection(EnumFacing direction) {
        this.direction = direction;

        world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);

        markDirty();
    }

    public EnumFacing getDirection() {
        return direction;
    }
}
