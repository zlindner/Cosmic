package zachy.ultio.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.util.MultiblockUtil;

public class TileIndustrialBlastFurnace extends TileBase implements ITickable, ISidedInventory {

    private boolean complete = false;
    private boolean _complete = false;
    private int heat = 0;

    public TileIndustrialBlastFurnace() {

    }

    private boolean verifyStructure() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite());

        int _heat = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && (y == 1 || y == 2) && z == 0) {
                        if (!MultiblockUtil.isAir(world, check)) {
                            if (MultiblockUtil.isLava(world, check)) {
                                _heat += 250;
                            } else {
                                return false;
                            }
                        }
                    } else if (!MultiblockUtil.isCasing(world, check)) {
                        return false;
                    } else {
                        _heat += 30 + (world.getBlockState(check).getBlock().getMetaFromState(world.getBlockState(check)) * 20);
                    }
                }
            }
        }

        heat = _heat;

        return true;
    }

    // complete or valid? what sounds better idk :^)
    public boolean isComplete() {
        return complete;
    }

    public int getHeat() {
        return heat;
    }

    @Override
    public void update() {
        counter++;

        if (counter == 20) {
            complete = verifyStructure();

            if (_complete != complete) {
                _complete = complete;

                if (world.isRemote) {
                    IBlockState state = world.getBlockState(pos);

                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }

            counter = 0;
        }

        if (!complete) {
            return;
        }
    }

    // required to read / write structure validity?
    @Override
    public NBTTagCompound writeUpdate(NBTTagCompound tag) {
        super.writeUpdate(tag);

        tag.setBoolean(Lib.NBT.COMPLETE, complete);

        return tag;
    }

    @Override
    public void readUpdate(NBTTagCompound tag) {
        super.readUpdate(tag);

        tag.getBoolean(Lib.NBT.COMPLETE);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
