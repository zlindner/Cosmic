package zachy.cosmic.item.base;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zachy.cosmic.client.IModelRegister;
import zachy.cosmic.core.CosmicTab;
import zachy.cosmic.core.Lib;

public class ItemBase extends Item implements IModelRegister {

    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(Lib.MOD_ID, name));

        setCreativeTab(CosmicTab.INSTANCE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
