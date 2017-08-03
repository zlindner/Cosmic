package zachy.cosmic.apiimpl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import zachy.cosmic.api.IAPI;
import zachy.cosmic.api.recipe.IBlastFurnaceRegistry;
import zachy.cosmic.api.recipe.IGrinderRegistry;
import zachy.cosmic.api.util.IComparer;
import zachy.cosmic.apiimpl.recipe.BlastFurnaceRegistry;
import zachy.cosmic.apiimpl.recipe.GrinderRegistry;
import zachy.cosmic.apiimpl.util.Comparer;

import javax.annotation.Nonnull;

public class API implements IAPI {

    private static final IAPI INSTANCE = new API();

    private IComparer comparer = new Comparer();
    private IBlastFurnaceRegistry blastFurnaceRegistry = new BlastFurnaceRegistry();
    private IGrinderRegistry grinderRegistry = new GrinderRegistry();

    public static IAPI instance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    public IComparer getComparer() {
        return comparer;
    }

    @Nonnull
    @Override
    public IBlastFurnaceRegistry getBlastFurnaceRegistry() {
        return blastFurnaceRegistry;
    }

    @Nonnull
    @Override
    public IGrinderRegistry getGrinderRegistry() {
        return grinderRegistry;
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
