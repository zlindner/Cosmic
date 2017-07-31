package zachy.cosmic.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import zachy.cosmic.common.core.util.MultiBlockUtils;

import javax.annotation.Nullable;

public class TileIndustrialGrinder extends TileMultiBlockBase implements IFluidTank {

    FluidTank tank = new FluidTank(4000);

    private final int INPUT_SLOTS[] = {0};
    private final int OUTPUT_SLOTS[] = {1, 2, 3, 4};

    public TileIndustrialGrinder() {
        setValid(false);
        setWorking(false);
        setProgress(0);

        inventory = NonNullList.withSize(5, ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite()).offset(EnumFacing.DOWN);

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && y == 1 && z == 0) {
                        if (!MultiBlockUtils.isWater(world, check)) {
                            return false;
                        }
                    } else if ((y == 0 || y == 2) && !MultiBlockUtils.isStandardCasing(world, check)) {
                        return false;
                    } else if (x != 0 && y == 1 && z != 0 && !MultiBlockUtils.isIntermediateCasing(world, check)) {
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
    public double getMaxStored() {
        return 0;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public void onInventoryChanged() {

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return new int[]{INPUT_SLOTS[0]};
        } else if (side == EnumFacing.DOWN) {
            return new int[0];
        } else {
            return OUTPUT_SLOTS;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index == INPUT_SLOTS[0];
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == OUTPUT_SLOTS[0] || index == OUTPUT_SLOTS[1] || index == OUTPUT_SLOTS[2] || index == OUTPUT_SLOTS[3];
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return null;
    }

    @Override
    public int getFluidAmount() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        return null;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }
}