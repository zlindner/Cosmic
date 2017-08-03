package zachy.cosmic.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.api.recipe.IBlastFurnaceRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.MultiBlockUtils;
import zachy.cosmic.common.core.util.WorldUtils;

public class TileIndustrialBlastFurnace extends TileMultiBlockBase {

    private IBlastFurnaceRecipe recipe;

    private int heat = 0;

    private final int INPUT_SLOTS[] = {0, 1};
    private final int OUTPUT_SLOTS[] = {2, 3};

    //TODO adjust internal energy storage? maybe 0?
    //TODO investigate client / server de-sync when removing item from output slot (fixed i think)
    public TileIndustrialBlastFurnace() {
        setValid(false);
        setWorking(false);
        setProgress(0);

        inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite());

        int _heat = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && (y == 1 || y == 2) && z == 0) {
                        if (!MultiBlockUtils.isAir(world, check)) {
                            if (MultiBlockUtils.isLava(world, check)) {
                                _heat += 250;
                            } else {
                                return false;
                            }
                        }
                    } else if (!MultiBlockUtils.isAnyCasing(world, check)) {
                        return false;
                    } else {
                        IBlockState state = world.getBlockState(check);

                        _heat += 30 + (state.getBlock().getMetaFromState(state) * 20);
                    }
                }
            }
        }

        heat = _heat;

        return true;
    }

    public int getHeat() {
        return heat;
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

    public void onInventoryChanged() {
        IBlastFurnaceRecipe _recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);

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

        if (recipe != null && heat < recipe.getHeat()) {
            return;
        }

        if (getEnergy() < 0) {
            return;
        }

        if (isWorking()) {
            if (recipe == null) {
                setWorking(false);
            } else if ((getStackInSlot(2).isEmpty() && getStackInSlot(3).isEmpty()
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(0), getStackInSlot(2))
                    && getStackInSlot(2).getCount() + recipe.getOutput(0).getCount() <= getStackInSlot(2).getMaxStackSize())
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(1), getStackInSlot(3))
                    && getStackInSlot(3).getCount() + recipe.getOutput(1).getCount() <= getStackInSlot(3).getMaxStackSize()))) {

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

                    for (int i = 0; i < INPUT_SLOTS.length; i++) {
                        ItemStack inputSlot = getStackInSlot(i);

                        if (!inputSlot.isEmpty()) {
                            inputSlot.shrink(recipe.getInput(i).get(0).getCount());
                        }
                    }

                    recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);

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

        recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);

        heat = tag.getInteger(Lib.NBT.HEAT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(Lib.NBT.HEAT, heat);

        return super.writeToNBT(tag);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return new int[]{INPUT_SLOTS[0]};
        } else if (side == EnumFacing.DOWN) {
            return new int[]{INPUT_SLOTS[1]};
        } else {
            return OUTPUT_SLOTS;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index == INPUT_SLOTS[0] || index == INPUT_SLOTS[1];
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == OUTPUT_SLOTS[0] || index == OUTPUT_SLOTS[1];
    }
}
