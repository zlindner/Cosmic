package zachy.cosmic.common.tile;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.api.recipe.sawmill.ISawmillRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.core.util.MultiBlockUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.base.TileMultiBlockBase;

import javax.annotation.Nullable;

public class TileSawmill extends TileMultiBlockBase implements IFluidHandler {

    private FluidTank tank = new FluidTank(16000);

    private ISawmillRecipe recipe;

    private final int INPUT_SLOTS[] = {0};
    private final int OUTPUT_SLOTS[] = {1, 2};

    private boolean wasFilled = false;

    public TileSawmill() {
        setValid(false);
        setWorking(false);
        setProgress(0);

        inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(EnumFacing.DOWN);

        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                BlockPos check = start.add(x, 0, z);

                if (x == 0 && z == 0) {
                    if (!MultiBlockUtils.isIntermediateCasing(world, check)) {
                        return false;
                    }
                } else if (!MultiBlockUtils.isStandardCasing(world, check)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int getDuration() {
        return recipe != null ? recipe.getDuration() : 0;
    }

    @Override
    public double getMaxInput() {
        return 32;
    }

    @Override
    public double getMaxStored() {
        return 3200;
    }

    @Override
    public int getSinkTier() {
        return 1;
    }

    @Override
    public void onInventoryChanged() {
        ISawmillRecipe _recipe = API.instance().getSawmillRegistry().getRecipe(this, tank);

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
            } else if ((getStackInSlot(1).isEmpty() && getStackInSlot(2).isEmpty()
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(0), getStackInSlot(1))
                    && getStackInSlot(1).getCount() + recipe.getOutput(0).getCount() <= getStackInSlot(1).getMaxStackSize())
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(1), getStackInSlot(2))
                    && getStackInSlot(2).getCount() + recipe.getOutput(1).getCount() <= getStackInSlot(2).getMaxStackSize()))) {

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

                    recipe = API.instance().getSawmillRegistry().getRecipe(this, tank);

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

        recipe = API.instance().getSawmillRegistry().getRecipe(this, tank);
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

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        wasFilled = true;

        return tank.fill(resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return tank.drain(resource, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    public FluidStack getFluidStack() {
        return tank.getFluid();
    }

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (isValid()) {
                return Light.builder().pos(pos).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
