package zachy.ultio.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import zachy.ultio.common.core.init.UltioBlocks;

public class TileIndustrialBlastFurnace extends TileBase implements ITickable, ISidedInventory {

    public boolean valid = false; // u ugly af fix pls
    private int internalHeat = 0;

    private int counter = 0;

    // lava = 250 heat ea
    // standard = 30 heat ea
    // reinforced = 50 heat ea
    // advanced = 70 heat ea

    public TileIndustrialBlastFurnace() {

    }
     // fix ugliness ... change to boolean? cache validity like heat?
    private void checkValidity() {
        BlockPos start = pos.offset(getDirection().getOpposite()).offset(getDirection().getOpposite());

        int heat = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (x == 0 && (y == 1 || y == 2) && z == 0) {
                        if (!isAir(check)) {
                            if (isLava(check)) {
                                heat += 250;
                            } else {
                                valid = false;

                                return;
                            }
                        }
                    } else if (!isCasing(check)) {
                        valid = false;

                        return;
                    } else {
                        heat += 30 + (world.getBlockState(check).getBlock().getMetaFromState(world.getBlockState(check)) * 20);
                    }
                }
            }
        }

        valid = true;

        internalHeat = heat;
    }

    public int getHeat() {
        return internalHeat;
    }

    @Override
    public void update() {
        counter++;

        if (counter == 20) {
            checkValidity();

            counter = 0;
        }

        if (!valid) {
            return;
        }
    }

    public boolean isCasing(BlockPos pos) {
        return world.getBlockState(pos).getBlock() == UltioBlocks.machineCasing;
    }

    public boolean isLava(BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.LAVA;
    }

    public boolean isAir(BlockPos pos) {
        return world.isAirBlock(pos);
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
