package zachy.cosmic.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zachy.cosmic.core.Lib;
import zachy.cosmic.item.base.ItemBase;

import javax.annotation.Nonnull;

public class ItemCell extends ItemBase {

    public ItemCell() {
        super(Lib.Items.CELL);

        setHasSubtypes(true);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        for (int i = 0; i < Lib.Items.CELL_TYPES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Lib.MOD_ID + ":" + Lib.Items.CELL_TYPES[i] + "_cell", "inventory"));
        }
    }
}
