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

public class BlockMachineFrame extends BlockBase {

    private static final IProperty<MachineFrameType> TYPE = PropertyEnum.create("type", MachineFrameType.class);

    public BlockMachineFrame() {
        super(Lib.Blocks.MACHINE_FRAME);

        setDefaultState(getDefaultState().withProperty(TYPE, MachineFrameType.BASIC));
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
        return getDefaultState().withProperty(TYPE, MachineFrameType.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (MachineFrameType type : MachineFrameType.values()) {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    @Override
    public void registerModel() {
        for (int i = 0; i < MachineFrameType.values().length; i++) {
            ModelHandler.registerBlock(this, i, getDefaultState().withProperty(TYPE, MachineFrameType.values()[i]));
        }
    }

}
