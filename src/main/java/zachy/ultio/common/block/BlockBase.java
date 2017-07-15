package zachy.ultio.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import zachy.ultio.common.core.Lib;

public class BlockBase extends Block {

    public BlockBase(String name) {
        super(Material.ROCK);

        setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(Lib.MOD_ID, name));
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}
