package zachy.cosmic.tile.base;

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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.ArrayUtils;
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.EnergyUtils;
import zachy.cosmic.core.util.StackUtils;
import zachy.cosmic.core.util.WorldUtils;

import javax.annotation.Nullable;

@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public abstract class TileMachine extends TileBase implements ITickable, IEnergySink, ISidedInventory, IFluidHandler, ILightProvider {

    protected String name;
    protected boolean working;
    protected int progress;
    protected double energy;
    private boolean enet;

    protected NonNullList<ItemStack> inventory;
    protected FluidTank tank;
    protected IMachineRecipe recipe;

    public boolean isWorking() {
        return working;
    }

    public int getProgress() {
        return progress;
    }

    public double getEnergy() {
        return energy;
    }

    public boolean addedToEnet() {
        return enet;
    }

    public int getDuration() {
        return recipe != null ? recipe.getDuration() : 0;
    }

    public void sync() {
        markDirty();

        WorldUtils.updateBlock(world, pos);
    }

    public void onInventoryChanged() {
        IMachineRecipe _recipe;

        if (tank != null) {
            _recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs(), tank);
        } else {
            _recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());
        }

        if (_recipe != recipe) {
            progress = 0;
        }

        recipe = _recipe;

        sync();
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

        if (tank != null) {
            recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs(), tank);

            tank.setFluid(FluidStack.loadFluidStackFromNBT(tag));
        } else {
            recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        for (int i = 0; i < inventory.size(); i++) {
            StackUtils.writeItems(this, i, tag);
        }

        tag.setBoolean(Lib.NBT.WORKING, working);
        tag.setInteger(Lib.NBT.PROGRESS, progress);

        tag.setDouble(Lib.NBT.ENERGY, energy);

        if (tank != null) {
            if (tank.getFluid() != null) {
                tank.getFluid().writeToNBT(tag);
            }
        }

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

    public void setEnergy(double energy) {
        this.energy = energy;

        if (energy > getMaxStored()) {
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

    public double getMaxOutput() {
        return 0;
    }

    public double getMaxStored() {
        return 0;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.min(getMaxStored() - energy, getMaxInput());
    }

    @Override
    public int getSinkTier() {
        return EnergyUtils.getSink(getMaxInput());
    }

    @Override
    public double injectEnergy(EnumFacing enumFacing, double energy, double voltage) {
        setEnergy(this.energy + energy);

        return 0;
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == getDirection() || side == getDirection().getOpposite()) {
            return new int[0];
        }

        return ArrayUtils.addAll(INPUT_SLOTS, OUTPUT_SLOTS);
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

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (doFill) {
            tank.fill(resource, true);

            onInventoryChanged();
        }

        return tank.fill(resource, false);
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
}
