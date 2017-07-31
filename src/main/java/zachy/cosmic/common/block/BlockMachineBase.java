package zachy.cosmic.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.common.core.util.StackUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.item.ItemBlockBase;

import java.util.Arrays;

public class BlockMachineBase extends BlockBase {

    //TODO add basic / intermediate / advanced boolean method, drop machine block accordingly
    protected static final PropertyDirection DIRECTION = PropertyDirection.create("direction", Arrays.asList(EnumFacing.HORIZONTALS));

    public BlockMachineBase(String name) {
        super(name);

        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 0);

        setDefaultState(getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH));
    }

    @Override
    public Item createItemBlock() {
        ItemBlockBase itemBlock = new ItemBlockBase(this);

        itemBlock.setHorizontal();

        return itemBlock;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = WorldUtils.getTile(world, pos);

        StackUtils.dropInventory(world, pos, (IInventory) tile);

        super.breakBlock(world, pos, state);
    }
}
