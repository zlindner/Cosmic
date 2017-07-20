package zachy.ultio.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import zachy.ultio.client.core.IModelRegister;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.UltioTab;
import zachy.ultio.common.core.Util;
import zachy.ultio.common.item.ItemBlockBase;
import zachy.ultio.common.tile.TileBase;

import javax.annotation.Nullable;

public class BlockBase extends Block implements IModelRegister {

    public BlockBase(String name) {
        super(Material.ROCK);

        setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(Lib.MOD_ID, name));
        setCreativeTab(UltioTab.instance);
    }

    public Item createItem() {
        return new ItemBlockBase(this, getDirection(), false);
    }

    protected BlockStateContainer.Builder createBlockStateBuilder() {
        BlockStateContainer.Builder builder = new BlockStateContainer.Builder(this);

        if (getDirection() != null) {
            builder.add(getDirection().getProperty());
        }

        return builder;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return createBlockStateBuilder().build();
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
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos) {
        if (getDirection() != null) {
            TileEntity tile = Util.getTileEntitySafely(access, pos);

            if (tile instanceof TileBase) {
                return state.withProperty(getDirection().getProperty(), ((TileBase) tile).getDirection());
            }
        }

        return state;
    }

    @Nullable
    public Direction getDirection() {
        return null;
    }

    @Override
    public void registerModel() {
        // NO-OP
    }
}
