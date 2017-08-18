package zachy.cosmic.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.MultiblockUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.base.TileMultiblockController;

public class TileDistillationTower extends TileMultiblockController {

    public TileDistillationTower() {
        name = Lib.Blocks.BLAST_FURNACE;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3, 4};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
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

    @Override
    public void update() {
        super.update();

        if (world.isRemote) {
            return;
        }

        if (!valid) {
            return;
        }

        if (getEnergy() < 0) {
            return;
        }

        if (isWorking()) {
            if (recipe == null) {
                working = false;
            } else if ((getStackInSlot(2).isEmpty() && getStackInSlot(3).isEmpty()
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(0), getStackInSlot(2))
                    && getStackInSlot(2).getCount() + recipe.getOutput(0).getCount() <= getStackInSlot(2).getMaxStackSize())
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(1), getStackInSlot(3))
                    && getStackInSlot(3).getCount() + recipe.getOutput(1).getCount() <= getStackInSlot(3).getMaxStackSize()))) {

                if (getEnergy() >= recipe.getEnergy()) {
                    drainEnergy(recipe.getEnergy());
                } else {
                    progress = 0;

                    return;
                }

                progress++;

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

                    recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());

                    progress = 0;
                }

                markDirty();

                WorldUtils.updateBlock(world, pos);
            }
        } else if (recipe != null) {
            working = true;
        }
    }

    @Override
    public double getMaxInput() {
        return 128;
    }
}