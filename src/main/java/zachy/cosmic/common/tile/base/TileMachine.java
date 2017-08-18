package zachy.cosmic.common.tile.base;

import elucent.albedo.lighting.ILightProvider;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.Info;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.EnergyUtils;
import zachy.cosmic.common.core.util.StackUtils;
import zachy.cosmic.common.core.util.WorldUtils;

@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public abstract class TileMachine extends TileBase implements ITickable, IEnergySink, ISidedInventory, ILightProvider {

    protected boolean working;
    protected int progress;

    private double energy;
    private boolean enet;

    protected NonNullList<ItemStack> inventory;

    protected IMachineRecipe recipe;

    protected String name;

    public boolean isWorking() {
        return working;
    }

    public int getProgress() {
        return progress;
    }

    public int getDuration() {
        return recipe != null ? recipe.getDuration() : 0;
    }

    public void onInventoryChanged() {
        IMachineRecipe _recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());

        if (_recipe != recipe) {
            progress = 0;
        }

        recipe = _recipe;

        markDirty();

        WorldUtils.updateBlock(world, pos);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        for (int i = 0; i < inventory.size(); i++) {
            StackUtils.readItems(this, i, tag);
        }

        if (tag.hasKey(Lib.NBT.WORKING)) {
            working = tag.getBoolean(Lib.NBT.WORKING);
        }

        if (tag.hasKey(Lib.NBT.PROGRESS)) {
            progress = tag.getInteger(Lib.NBT.PROGRESS);
        }

        energy = tag.getDouble(Lib.NBT.ENERGY);

        recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        for (int i = 0; i < inventory.size(); i++) {
            StackUtils.writeItems(this, i, tag);
        }

        tag.setBoolean(Lib.NBT.WORKING, working);
        tag.setInteger(Lib.NBT.PROGRESS, progress);

        tag.setDouble(Lib.NBT.ENERGY, energy);

        return super.writeToNBT(tag);
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onChunkUnload() {
        unload();
    }

    @Override
    public void invalidate() {
        super.invalidate();

        unload();
    }

    private void load() {
        if (!enet && !FMLCommonHandler.instance().getEffectiveSide().isClient() && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));

            enet = true;
        }
    }

    private void unload() {
        if (enet && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));

            enet = false;
        }

    }

    //Don't remove, may need for gui's in future
    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;

        if (getEnergy() > getMaxStored()) {
            setEnergy(getMaxStored());
        } else if (energy < 0) {
            setEnergy(0);
        }
    }

    public double drainEnergy(double extract) {
        if (extract > energy) {
            double tempEnergy = energy;

            setEnergy(0);

            return tempEnergy;
        }

        setEnergy(energy - extract);

        return extract;
    }

    public abstract double getMaxInput();

    public double getMaxStored() {
        return getMaxInput() * 10;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.min(getMaxStored() - getEnergy(), getMaxInput());
    }

    @Override
    public int getSinkTier() {
        return EnergyUtils.getSink(getMaxInput());
    }

    @Override
    public double injectEnergy(EnumFacing enumFacing, double energy, double voltage) {
        setEnergy(getEnergy() + energy);

        return 0;
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index < getInputs();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index > getInputs();
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
        return true;
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
        return true;
    }
}