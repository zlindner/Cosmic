package zachy.cosmic.common.tile;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.api.recipe.IBlastFurnaceRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.MultiBlockUtils;
import zachy.cosmic.common.core.util.StackUtils;
import zachy.cosmic.common.core.util.WorldUtils;

@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class TileIndustrialBlastFurnace extends TileBase implements ITickable, ISidedInventory, ILightProvider {

    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    private IBlastFurnaceRecipe recipe;

    private boolean valid = false;
    private boolean working = false;

    private int heat = 0;
    private int progress = 0;

    //TODO add to ic2 enet
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
                        if (!MultiBlockUtils.isAir(world, check)) {
                            if (MultiBlockUtils.isLava(world, check)) {
                                _heat += 250;
                            } else {
                                return false;
                            }
                        }
                    } else if (!MultiBlockUtils.isCasing(world, check)) {
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

    public boolean isWorking() {
        return working;
    }

    public int getHeat() {
        return heat;
    }

    public int getProgress() {
        return progress;
    }

    public int getDuration() {
        return recipe != null ? recipe.getDuration() : 0;
    }

    public IBlastFurnaceRecipe getRecipe() {
        return recipe;
    }

    private void onInventoryChanged() {
        IBlastFurnaceRecipe _recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);

        if (_recipe != recipe) {
            progress = 0;
        }

        recipe = _recipe;

        markDirty();

        WorldUtils.updateBlock(world, pos);
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        counter++;

        if (counter == 20) {
            valid = verifyStructure();

            markDirty();

            WorldUtils.updateBlock(world, pos);

            counter = 0;
        }

        if (!valid) {
            return;
        }

        if (recipe != null && getHeat() < recipe.getHeat()) {
            return;
        }

        if (working) {
            if (recipe == null) {
                working = false;
            } else if ((getStackInSlot(2).isEmpty() && getStackInSlot(3).isEmpty()
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(0), getStackInSlot(2))
                    && getStackInSlot(2).getCount() + recipe.getOutput(0).getCount() <= getStackInSlot(2).getMaxStackSize())
                    || (API.instance().getComparer().isEqualNoQuantity(recipe.getOutput(1), getStackInSlot(3))
                    && getStackInSlot(3).getCount() + recipe.getOutput(1).getCount() <= getStackInSlot(3).getMaxStackSize()))) {

                progress++;

                if (progress >= recipe.getDuration()) {
                    for (int i = 0; i < 2; i++) {
                        ItemStack outputSlot = getStackInSlot(i + 2);

                        if (outputSlot.isEmpty()) {
                            setInventorySlotContents(i + 2, recipe.getOutput(i).copy());
                        } else {
                            outputSlot.grow(recipe.getOutput(i).getCount());
                        }
                    }

                    for (int i = 0; i < 2; i++) {
                        ItemStack inputSlot = getStackInSlot(i);

                        if (!inputSlot.isEmpty()) {
                            inputSlot.shrink(recipe.getInput(i).get(0).getCount());
                        }
                    }

                    recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);
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
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        boolean clientValid = isValid();

        super.onDataPacket(net, packet);

        if (world.isRemote) {
            boolean serverValid = isValid();

            if (serverValid != clientValid) {
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        StackUtils.readItems(this, 0, tag);
        StackUtils.readItems(this, 1, tag);
        StackUtils.readItems(this, 2, tag);
        StackUtils.readItems(this, 3, tag);

        recipe = API.instance().getBlastFurnaceRegistry().getRecipe(this);

        valid = tag.getBoolean(Lib.NBT.VALID);
        heat = tag.getInteger(Lib.NBT.HEAT);

        if (tag.hasKey(Lib.NBT.WORKING)) {
            working = tag.getBoolean(Lib.NBT.WORKING);
        }

        if (tag.hasKey(Lib.NBT.PROGRESS)) {
            progress = tag.getInteger(Lib.NBT.PROGRESS);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        StackUtils.writeItems(this, 0, tag);
        StackUtils.writeItems(this, 1, tag);
        StackUtils.writeItems(this, 2, tag);
        StackUtils.writeItems(this, 3, tag);

        tag.setBoolean(Lib.NBT.VALID, valid);
        tag.setInteger(Lib.NBT.HEAT, heat);
        tag.setBoolean(Lib.NBT.WORKING, working);
        tag.setInteger(Lib.NBT.PROGRESS, progress);

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

        onInventoryChanged();

        return stackRemoved;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory.get(index);

        if (!stack.isEmpty()) {
            inventory.set(index, ItemStack.EMPTY);

            onInventoryChanged();
        }

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        onInventoryChanged();
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
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (isValid()) {
                return Light.builder().pos(pos.offset(getDirection())).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos.offset(getDirection())).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
