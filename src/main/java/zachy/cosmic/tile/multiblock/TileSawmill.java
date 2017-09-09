package zachy.cosmic.tile.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTank;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileSawmill extends TileMultiblockController {

    public TileSawmill() {
        name = Lib.Blocks.SAWMILL;

        INPUT_SLOTS = new int[]{0};
        OUTPUT_SLOTS = new int[]{1, 2};

        standardOrientation = false;

        maxInput = 32;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);

        tank = new FluidTank(16000);
    }

    @Override
    protected boolean valid() {
        BlockPos start = pos.offset(EnumFacing.DOWN);

        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                BlockPos check = start.add(x, 0, z);

                if (x == 0 && z == 0) {
                    if (!MultiblockUtils.isIntermediateCasing(world, check)) {
                        return false;
                    }
                } else if (!MultiblockUtils.isBasicCasing(world, check)) {
                    return false;
                }
            }
        }

        return true;
    }
}
