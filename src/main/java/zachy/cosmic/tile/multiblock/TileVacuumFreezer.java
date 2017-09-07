package zachy.cosmic.tile.multiblock;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileVacuumFreezer extends TileMultiblockController {

    public TileVacuumFreezer() {
        name = Lib.Blocks.VACUUM_FREEZER;

        INPUT_SLOTS = new int[] {0};
        OUTPUT_SLOTS = new int[] {1};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    //TODO still dont like name of method
    @Override
    protected boolean verifyStructure() {
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

    @Override
    public double getMaxInput() {
        return 128;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return new int[]{INPUT_SLOTS[0]};
        } else if (side == EnumFacing.DOWN) {
            return new int[]{INPUT_SLOTS[0]};
        } else {
            return OUTPUT_SLOTS;
        }
    }

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (valid) {
                return Light.builder().pos(pos).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
