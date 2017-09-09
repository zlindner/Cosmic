package zachy.cosmic.tile.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileVacuumFreezer extends TileMultiblockController {

    public TileVacuumFreezer() {
        name = Lib.Blocks.VACUUM_FREEZER;

        INPUT_SLOTS = new int[]{0};
        OUTPUT_SLOTS = new int[]{1};

        alternateOrientation = true;

        maxInput = 128;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean valid() {
        BlockPos start = pos.offset(EnumFacing.DOWN).offset(EnumFacing.DOWN).offset(EnumFacing.DOWN);

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (y == 0 || y == 2) {
                        if (x == 0 && z == 0) {
                            if (!MultiblockUtils.isAdvancedCasing(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiblockUtils.isIntermediateCasing(world, check)) {
                                return false;
                            }
                        }
                    } else {
                        if (x == 0 && z == 0) {
                            if (!MultiblockUtils.isAir(world, check)) {
                                return false;
                            }
                        } else if (x == 0 || z == 0) {
                            if (!MultiblockUtils.isAdvancedCasing(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiblockUtils.isIntermediateCasing(world, check)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
