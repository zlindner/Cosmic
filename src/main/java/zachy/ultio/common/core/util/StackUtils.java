package zachy.ultio.common.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;
import zachy.ultio.apiimpl.API;
import zachy.ultio.common.core.Lib;

import java.util.HashMap;
import java.util.Map;

public class StackUtils {

    private static final Map<Integer, Boolean> OREDICT_EQUIVALENCY_CACHE = new HashMap<>();

    private static final NonNullList<Object> EMPTY_NON_NULL_LIST = NonNullList.create();

    public static boolean areStacksEquivalent(ItemStack left, ItemStack right) {
        int code = API.instance().getItemStackHashCode(left, false);
        code = 31 * code + API.instance().getItemStackHashCode(right, false);

        if (OREDICT_EQUIVALENCY_CACHE.containsKey(code)) {
            return OREDICT_EQUIVALENCY_CACHE.get(code);
        }

        int[] leftIds = OreDictionary.getOreIDs(left);
        int[] rightIds = OreDictionary.getOreIDs(right);

        for (int i : rightIds) {
            if (ArrayUtils.contains(leftIds, i)) {
                OREDICT_EQUIVALENCY_CACHE.put(code, true);

                return true;
            }
        }

        OREDICT_EQUIVALENCY_CACHE.put(code, false);

        return false;
    }

    public static void writeItems(IInventory inventory, int id, NBTTagCompound tag) {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                NBTTagCompound stackTag = new NBTTagCompound();

                stackTag.setInteger(Lib.NBT.SLOT, i);

                inventory.getStackInSlot(i).writeToNBT(stackTag);

                tagList.appendTag(stackTag);
            }
        }

        tag.setTag(String.format(Lib.NBT.INVENTORY, id), tagList);
    }

    public static void readItems(IInventory inventory, int id, NBTTagCompound tag) {
        String name = String.format(Lib.NBT.INVENTORY, id);

        if (tag.hasKey(name)) {
            NBTTagList tagList = tag.getTagList(name, Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.tagCount(); i++) {
                int slot = tagList.getCompoundTagAt(i).getInteger(Lib.NBT.SLOT);

                ItemStack stack = new ItemStack(tagList.getCompoundTagAt(i));

                if (!stack.isEmpty()) {
                    inventory.setInventorySlotContents(slot, stack);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> NonNullList<T> emptyNonNullList() {
        return (NonNullList<T>) EMPTY_NON_NULL_LIST;
    }
}
