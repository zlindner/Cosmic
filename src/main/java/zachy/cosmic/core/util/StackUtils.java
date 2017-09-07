package zachy.cosmic.core.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import zachy.cosmic.core.Lib;

import java.util.Random;

public class StackUtils {

    private static final NonNullList<Object> EMPTY_NON_NULL_LIST = NonNullList.create();

    public static boolean isItemEqual(ItemStack stackA, ItemStack stackB, boolean matchDamage, boolean matchNBT) {
        if (stackA.isEmpty() || stackB.isEmpty()) {
            return false;
        }

        if (stackA.getItem() != stackB.getItem()) {
            return false;
        }
        if (matchDamage && stackA.getHasSubtypes()) {
            if (!isWildcard(stackA) && !isWildcard(stackB)) {
                if (stackA.getItemDamage() != stackB.getItemDamage()) {
                    return false;
                }
            }
        }
        if (matchNBT) {
            NBTTagCompound baseTag = stackA.getTagCompound();
            if (baseTag != null && !baseTag.equals(stackB.getTagCompound())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCraftingEquivalent(ItemStack stackA, ItemStack stackB, boolean oreDictionary) {
        if (isItemEqual(stackA, stackB, true, false)) {
            return true;
        }

        if (oreDictionary) {
            int[] idBase = OreDictionary.getOreIDs(stackA);

            if (idBase.length > 0) {
                for (int id : idBase) {
                    for (ItemStack stack : OreDictionary.getOres(OreDictionary.getOreName(id))) {
                        if (stackB.getItem() == stack.getItem() && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stackB.getItemDamage() == stack.getItemDamage())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean isWildcard(ItemStack stack) {
        return isWildcard(stack.getItemDamage());
    }

    public static boolean isWildcard(int damage) {
        return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
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

    public static void dropInventory(World world, BlockPos pos, IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);

            if (!stack.isEmpty()) {
                spawnStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    public static void spawnStack(World world, double x, double y, double z, ItemStack stack) {
        Random random = new Random();

        float f = random.nextFloat() * 0.8F + 0.1F;
        float f1 = random.nextFloat() * 0.8F + 0.1F;
        float f2 = random.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty()) {
            EntityItem entityitem = new EntityItem(world, x + (double) f, y + (double) f1, z + (double) f2, stack.splitStack(random.nextInt(21) + 10));

            entityitem.motionX = random.nextGaussian() * 0.05000000074505806D;
            entityitem.motionY = random.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            entityitem.motionZ = random.nextGaussian() * 0.05000000074505806D;

            world.spawnEntity(entityitem);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> NonNullList<T> emptyNonNullList() {
        return (NonNullList<T>) EMPTY_NON_NULL_LIST;
    }
}
