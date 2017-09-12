package zachy.cosmic.block.base;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import zachy.cosmic.core.handler.ModelHandler;
import zachy.cosmic.core.util.WorldUtils;
import zachy.cosmic.tile.base.TileMultiblockController;

import java.util.Arrays;

public class BlockMultiblockController extends BlockMachine {

    protected static final PropertyDirection DIRECTION = PropertyDirection.create("direction", Arrays.asList(EnumFacing.HORIZONTALS));
    protected static final PropertyBool VALID = PropertyBool.create("valid");

    public BlockMultiblockController(String name) {
        super(name);
    }

    @Override
    public IBlockState getBaseState() {
        return getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(VALID, false);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION, VALID);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = WorldUtils.getTileSafely(world, pos);

        if (tile instanceof TileMultiblockController) {
            return getDefaultState().withProperty(DIRECTION, ((TileMultiblockController) tile).getDirection()).withProperty(VALID, ((TileMultiblockController) tile).isValid());
        }

        return state;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(VALID, false));
    }

    @Override
    public boolean isIntermediate() {
        return true;
    }
}
