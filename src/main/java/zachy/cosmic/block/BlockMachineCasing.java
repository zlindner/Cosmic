package zachy.cosmic.block;

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
import zachy.cosmic.apiimpl.state.enums.MachineCasingType;
import zachy.cosmic.core.handler.ModelHandler;
import zachy.cosmic.block.base.BlockBase;
import zachy.cosmic.core.Lib;
import zachy.cosmic.item.base.ItemBlockBase;

public class BlockMachineCasing extends BlockBase {

    private static final IProperty<MachineCasingType> TYPE = PropertyEnum.create("type", MachineCasingType.class);

    public BlockMachineCasing() {
        super(Lib.Blocks.MACHINE_CASING);

        setDefaultState(getDefaultState().withProperty(TYPE, MachineCasingType.BASIC));
    }

    @Override
    public Item createItemBlock() {
        ItemBlockBase itemBlock = new ItemBlockBase(this);

        itemBlock.setHasMeta();

        return itemBlock;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
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
