package zachy.cosmic.apiimpl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import zachy.cosmic.api.IAPI;
import zachy.cosmic.api.recipe.IMachineRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.recipe.MachineRegistry;
import zachy.cosmic.apiimpl.util.Comparer;
import zachy.cosmic.common.core.Lib;

import javax.annotation.Nonnull;

public class API implements IAPI {

    private static final IAPI INSTANCE = new API();

    private IComparer comparer = new Comparer();

    private IMachineRegistry blastFurnaceRegistry = new MachineRegistry();
    private IMachineRegistry compressorRegistry = new MachineRegistry();
    private IMachineRegistry grinderRegistry = new MachineRegistry();
    private IMachineRegistry sawmillRegistry = new MachineRegistry();
    private IMachineRegistry vacuumFreezerRegistry = new MachineRegistry();
    private IMachineRegistry distillationTowerRegistry = new MachineRegistry();

    public static IAPI instance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    public IComparer getComparer() {
        return comparer;
    }

    @Override
    public IMachineRegistry getMachineRegistry(String machine) {
        switch (machine) {
            case Lib.Blocks.BLAST_FURNACE:
                return blastFurnaceRegistry;
            case Lib.Blocks.COMPRESSOR:
                return compressorRegistry;
            case Lib.Blocks.GRINDER:
                return grinderRegistry;
            case Lib.Blocks.SAWMILL:
                return sawmillRegistry;
            case Lib.Blocks.VACUUM_FREEZER:
                return vacuumFreezerRegistry;
            case Lib.Blocks.DISTILLATION_TOWER:
                return distillationTowerRegistry;
        }

        return null;
    }

    @Override
    public int getItemStackHashCode(ItemStack stack, boolean tag) {
        int result = stack.getItem().hashCode();
        result = 31 * result + (stack.getItemDamage() + 1);

        if (tag && stack.hasTagCompound()) {
            result = calcHashCode(stack.getTagCompound(), result);
        }

        return result;
    }

    private int calcHashCode(NBTBase tag, int result) {
        if (tag instanceof NBTTagCompound) {
            result = calcHashCode((NBTTagCompound) tag, result);
        } else if (tag instanceof NBTTagList) {
            result = calcHashCode((NBTTagList) tag, result);
        } else {
            result = 31 * result + tag.hashCode();
        }

        return result;
    }

    private int calcHashCode(NBTTagCompound tag, int result) {
        for (String key : tag.getKeySet()) {
            result = 31 * result + key.hashCode();
            result = calcHashCode(tag.getTag(key), result);
        }

        return result;
    }

    private int calcHashCode(NBTTagList tag, int result) {
        for (int i = 0; i < tag.tagCount(); ++i) {
            result = calcHashCode(tag.get(i), result);
        }

        return result;
    }
}
