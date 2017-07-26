package zachy.ultio.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import zachy.ultio.common.core.init.UltioBlocks;

public class UltioTab extends CreativeTabs {

    public static final UltioTab instance = new UltioTab();

    public UltioTab() {
        super(Lib.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(UltioBlocks.machineFrame);
    }
}
