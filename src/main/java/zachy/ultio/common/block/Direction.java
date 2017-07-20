package zachy.ultio.common.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public enum Direction {
    ANY(EnumFacing.VALUES),
    ANY_FACE_PLAYER(EnumFacing.VALUES),
    HORIZONTAL(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST);

    private final PropertyDirection property;

    Direction(EnumFacing... allowed) {
        property = PropertyDirection.create("direction", Arrays.asList(allowed));
    }

    public PropertyDirection getProperty() {
        return property;
    }

    public EnumFacing getFrom(EnumFacing facing, BlockPos pos, EntityLivingBase entity) {
        switch (this) {
            case ANY:
                return facing.getOpposite();
            case ANY_FACE_PLAYER:
                return EnumFacing.getDirectionFromEntityLiving(pos, entity);
            case HORIZONTAL:
                return entity.getHorizontalFacing().getOpposite();
            default:
                return null;
        }
    }
}
