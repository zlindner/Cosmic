package zachy.cosmic.common.tile;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.event.ExplosionEvent;
import ic2.api.info.Info;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import zachy.cosmic.common.core.Lib;

public abstract class TileMachineBase extends TileBase implements ITickable, IEnergySink {

    protected double energy;

    protected boolean enet;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        energy = tag.getDouble(Lib.NBT.ENERGY);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
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

    public void load() {
        if (!enet && !FMLCommonHandler.instance().getEffectiveSide().isClient() && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));

            enet = true;
        }
    }

    public void unload() {
        if (enet && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));

            enet = false;
        }

    }

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

    public abstract double getMaxStored();

    @Override
    public double getDemandedEnergy() {
        return Math.min(getMaxStored() - getEnergy(), getMaxInput());
    }

    @Override
    public int getSinkTier() {
        return 0;
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
}
