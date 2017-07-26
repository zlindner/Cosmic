package zachy.ultio.common.tile;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;
import zachy.ultio.common.Ultio;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.util.MultiblockUtil;

@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class TileIndustrialBlastFurnace extends TileBase implements ITickable, ISidedInventory, ILightProvider {

    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    private boolean valid = false;
    private boolean _valid = false;
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
                        IBlockState state = world.getBlockState(check);

                        _heat += 30 + (state.getBlock().getMetaFromState(state) * 20);
                    }
                }
            }
        }

        heat = _heat;

        return true;
    }

    public boolean isValid() {
        return valid;
    }

    public int getHeat() {
        return heat;
    }

    @Override
    public void update() {
        counter++;

        if (counter == 20) {
            valid = verifyStructure();

            if (_valid != valid) {
                _valid = valid;

                if (world.isRemote) {
                    IBlockState state = world.getBlockState(pos);

                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }

            counter = 0;
        }

        if (!valid) {
            return;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        valid = tag.getBoolean(Lib.NBT.VALID);
        ItemStackHelper.loadAllItems(tag, inventory);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setBoolean(Lib.NBT.VALID, valid);
        ItemStackHelper.saveAllItems(tag, inventory);

        return super.writeToNBT(tag);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.get(index);

        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack stackRemoved;

        if (stack.getCount() <= count) {
            stackRemoved = stack;
            inventory.set(index, ItemStack.EMPTY);
        } else {
            stackRemoved = stack.splitStack(count);
            if (stack.getCount() == 0) {
                setInventorySlotContents(index, ItemStack.EMPTY);
            }
        }

        markDirty();

        return stackRemoved;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory.get(index);

        if (!stack.isEmpty()) {
            inventory.set(index, ItemStack.EMPTY);
        }

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
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
        return index < 2;
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

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Ultio.INSTANCE.config.enableColouredLights) {
            if (isValid()) {
                return Light.builder().pos(pos.offset(getDirection())).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos.offset(getDirection())).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
