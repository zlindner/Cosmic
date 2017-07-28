package zachy.cosmic.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zachy.cosmic.client.core.IModelRegister;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.CosmicTab;
import zachy.cosmic.common.item.ItemBlockBase;

public class BlockBase extends Block implements IModelRegister {

    public BlockBase(String name) {
        super(Material.ROCK);

        setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(Lib.MOD_ID, name));
        setCreativeTab(CosmicTab.INSTANCE);
    }

    public Item createItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void registerModel() {
        // NO-OP
    }
}
