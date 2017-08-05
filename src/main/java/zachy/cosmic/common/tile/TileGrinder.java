package zachy.cosmic.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import zachy.cosmic.api.recipe.grinder.IGrinderRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.core.util.MultiBlockUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.base.TileMultiBlockBase;

public class TileGrinder extends TileMultiBlockBase implements IFluidHandler {

    private FluidTank tank = new FluidTank(16000);

    private IGrinderRecipe recipe;

    private final int INPUT_SLOTS[] = {0};
    private final int OUTPUT_SLOTS[] = {1, 2, 3};

    private boolean wasFilled = false;

    //TODO test / add fluid pipe compatibility
    //TODO remove last output slot from gui
    public TileGrinder() {
        setValid(false);
        setWorking(false);
        setProgress(0);

        inventory = NonNullList.withSize(4, ItemStack.EMPTY);
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

    public int getDuration() {
        return recipe != null ? recipe.getDuration() : 0;
    }

    @Override
    public double getMaxInput() {
        return 128;
    }

    @Override
    public double getMaxStored() {
        return 12800;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public void onInventoryChanged() {
        IGrinderRecipe _recipe = API.instance().getGrinderRegistry().getRecipe(this, tank);

        if (_recipe != recipe) {
            setProgress(0);
        }

        recipe = _recipe;

        markDirty();

        WorldUtils.updateBlock(world, pos);
    }

    @Override
    public void update() {
        super.update();

        if (world.isRemote) {
            return;
        }

        if (!isValid()) {
            return;
        }

        if (wasFilled) {
            onInventoryChanged();

            wasFilled = false;
        }

        if (recipe != null && recipe.getFluidAmount() > tank.getFluidAmount()) {
            return;
        }

        if (getEnergy() < 0) {
            return;
        }

        if (isWorking()) {
            if (recipe == null) {
                setWorking(false);
            } else if ((getStackInSlot(1).isEmpty() && getStackInSlot(2).isEmpty() && getStackInSlot(3).isEmpty()
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(0), getStackInSlot(1))
                    && getStackInSlot(1).getCount() + recipe.getOutput(0).getCount() <= getStackInSlot(1).getMaxStackSize())
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(1), getStackInSlot(2))
                    && getStackInSlot(2).getCount() + recipe.getOutput(1).getCount() <= getStackInSlot(2).getMaxStackSize()))
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(2), getStackInSlot(3))
                    && getStackInSlot(3).getCount() + recipe.getOutput(2).getCount() <= getStackInSlot(3).getMaxStackSize())) {

                if (getEnergy() >= recipe.getEnergy()) {
                    drainEnergy(recipe.getEnergy());
                } else {
                    setProgress(0);

                    return;
                }

                setProgress(getProgress() + 1);

                if (getProgress() >= recipe.getDuration()) {
                    for (int i = 0; i < OUTPUT_SLOTS.length; i++) {
                        ItemStack outputSlot = getStackInSlot(OUTPUT_SLOTS[i]);

                        if (outputSlot.isEmpty()) {
                            setInventorySlotContents(OUTPUT_SLOTS[i], recipe.getOutput(i).copy());
                        } else {
                            outputSlot.grow(recipe.getOutput(i).getCount());

                            markDirty();

                            WorldUtils.updateBlock(world, pos);
                        }
                    }

                    ItemStack inputSlot = getStackInSlot(INPUT_SLOTS[0]);

                    if (!inputSlot.isEmpty()) {
                        inputSlot.shrink(recipe.getInput().get(0).getCount());
                    }

                    drain(recipe.getFluidAmount(), true);

                    recipe = API.instance().getGrinderRegistry().getRecipe(this, tank);

                    setProgress(0);
                }

                markDirty();

                WorldUtils.updateBlock(world, pos);
            }
        } else if (recipe != null) {
            setWorking(true);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        tank.setFluid(FluidStack.loadFluidStackFromNBT(tag));

        recipe = API.instance().getGrinderRegistry().getRecipe(this, tank);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        if (tank.getFluid() != null) {
            tank.getFluid().writeToNBT(tag);
        }

        return super.writeToNBT(tag);
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

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index == INPUT_SLOTS[0];
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == OUTPUT_SLOTS[0] || index == OUTPUT_SLOTS[1] || index == OUTPUT_SLOTS[2];
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    //TODO possibly create custom implementation of FluidTank or IFluidHandler
    @Override
    public int fill(FluidStack resource, boolean doFill) {
        wasFilled = true;

        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return tank.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    public FluidStack getFluidStack() {
        return tank.getFluid();
    }
}
