package zachy.cosmic.tile.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileGrinder extends TileMultiblockController {

    public TileGrinder() {
        name = Lib.Blocks.GRINDER;

        INPUT_SLOTS = new int[] {0, 1};
        OUTPUT_SLOTS = new int[] {2, 3, 4, 5};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite()).offset(EnumFacing.DOWN);

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && y == 1 && z == 0) {
                        if (!MultiblockUtils.isWater(world, check)) {
                            return false;
                        }
                    } else if ((y == 0 || y == 2) && !MultiblockUtils.isBasicCasing(world, check)) {
                        return false;
                    } else if (x != 0 && y == 1 && z != 0 && !MultiblockUtils.isIntermediateCasing(world, check)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public double getMaxInput() {
        return 128;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return INPUT_SLOTS;
        } else if (side == EnumFacing.DOWN) {
            return new int[0];
        } else {
            return OUTPUT_SLOTS;
        }
    }
}
