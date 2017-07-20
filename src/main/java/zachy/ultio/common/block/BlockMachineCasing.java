package zachy.ultio.common.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import zachy.ultio.client.core.handler.ModelHandler;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.item.ItemBlockBase;

public class BlockMachineCasing extends BlockBase {

    private static final IProperty<MachineCasingType> TYPE = PropertyEnum.create("type", MachineCasingType.class);

    public BlockMachineCasing() {
        super(Lib.Blocks.MACHINE_CASING);

        setDefaultState(getDefaultState().withProperty(TYPE, MachineCasingType.STANDARD));
    }

    @Override
    public Item createItem() {
        return new ItemBlockBase(this, getDirection(), true);
    }

    @Override
    protected BlockStateContainer.Builder createBlockStateBuilder() {
        return super.createBlockStateBuilder().add(TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, MachineCasingType.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (MachineCasingType type : MachineCasingType.values()) {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Override
    public void registerModel() {
        for (int i = 0; i < MachineCasingType.values().length; i++) {
            ModelHandler.registerBlock(this, i, getDefaultState().withProperty(TYPE, MachineCasingType.values()[i]));
        }
    }
}
