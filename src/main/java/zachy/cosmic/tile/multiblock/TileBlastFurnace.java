package zachy.cosmic.tile.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.MultiblockUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

public class TileBlastFurnace extends TileMultiblockController {

    public TileBlastFurnace() {
        name = Lib.Blocks.BLAST_FURNACE;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3};

        maxInput = 128;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    protected boolean valid() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite());

        int _heat = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && (y == 1 || y == 2) && z == 0) {
                        if (!MultiblockUtils.isAir(world, check)) {
                            if (MultiblockUtils.isLava(world, check)) {
                                _heat += 250;
                            } else {
                                return false;
                            }
                        }
                    } else if (!MultiblockUtils.isAnyCasing(world, check)) {
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

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        heat = tag.getInteger(Lib.NBT.HEAT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(Lib.NBT.HEAT, heat);

        return super.writeToNBT(tag);
    }
}
