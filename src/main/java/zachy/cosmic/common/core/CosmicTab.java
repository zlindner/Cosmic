package zachy.cosmic.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import zachy.cosmic.common.core.init.ModBlocks;

public class CosmicTab extends CreativeTabs {

    public static final CosmicTab INSTANCE = new CosmicTab();

    public CosmicTab() {
        super(Lib.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.machine_frame);
    }
}
