package zachy.cosmic.tile.multiblock;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.ArrayUtils;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileSawmill extends TileMultiblockController {

    public TileSawmill() {
        name = Lib.Blocks.SAWMILL;

        INPUT_SLOTS = new int[] {0};
        OUTPUT_SLOTS = new int[] {1, 2};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);

        tank = new FluidTank(16000);
    }

    @Override
    protected boolean verifyStructure() {
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

    @Override
    public double getMaxInput() {
        return 32;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
            return new int[0];
        }

        return ArrayUtils.addAll(INPUT_SLOTS, OUTPUT_SLOTS);
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
