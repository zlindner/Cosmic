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

public class TileCompressor extends TileMultiblockController {

    public TileCompressor() {
        name = Lib.Blocks.COMPRESSOR;

        INPUT_SLOTS = new int[] {0, 1};
        OUTPUT_SLOTS = new int[] {2, 3};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(EnumFacing.DOWN).offset(EnumFacing.DOWN).offset(EnumFacing.DOWN);

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && y == 1 && z == 0) {
                        if (!MultiblockUtils.isAir(world, check)) {
                            return false;
                        }
                    } else if (y == 0 || y == 2) {
                        if (x != 0 && z != 0) {
                            if (!MultiblockUtils.isBasicCasing(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiblockUtils.isIntermediateCasing(world, check)) {
                                return false;
                            }
                        }
                    } else {
                        if (!MultiblockUtils.isIntermediateCasing(world, check)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public double getMaxInput() {
        return 32;
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
