package zachy.ultio.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class UltioTab extends CreativeTabs {

    public static final UltioTab instance = new UltioTab();

    public UltioTab() {
        super(Lib.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.CARROT_ON_A_STICK);
    }
}
