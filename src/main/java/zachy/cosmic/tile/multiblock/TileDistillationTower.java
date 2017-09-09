package zachy.cosmic.tile.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileDistillationTower extends TileMultiblockController {

    public TileDistillationTower() {
        name = Lib.Blocks.DISTILLATION_TOWER;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3, 4};

        maxInput = 128;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean valid() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite());

        for (int y = 0; y < 5; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (y == 1 || y == 3) {
                        if (x == 0 && z == 0) {
                            if (!MultiblockUtils.isAir(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiblockUtils.isAdvancedCasing(world, check)) {
                                return false;
                            }
                        }
                    } else if (y == 2) {
                        if (x == 0 && z == 0) {
                            if (!MultiblockUtils.isAir(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiblockUtils.isBasicCasing(world, check)) {
                                return false;
                            }
                        }
                    } else {
                        if (!MultiblockUtils.isBasicCasing(world, check)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
