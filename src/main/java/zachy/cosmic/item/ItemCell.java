package zachy.cosmic.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.core.Lib;
import zachy.cosmic.item.base.ItemBase;

import javax.annotation.Nonnull;

public class ItemCell extends ItemBase {

    public ItemCell() {
        super(Lib.Items.CELL);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> stacks) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < Lib.Items.CELL_TYPES.length; i++) {
                stacks.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Lib.Items.CELL_TYPES[Math.min(Lib.Items.CELL_TYPES.length - 1, stack.getItemDamage())] + "_cell";
    }
}
