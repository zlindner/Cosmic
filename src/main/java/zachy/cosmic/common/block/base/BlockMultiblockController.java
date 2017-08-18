package zachy.cosmic.common.block.base;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import zachy.cosmic.client.core.handler.ModelHandler;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.base.TileMultiblockController;

public class BlockMultiblockController extends BlockMachine {

    protected static final PropertyBool VALID = PropertyBool.create("valid");

    public BlockMultiblockController(String name) {
        super(name);

        setDefaultState(getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(VALID, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION, VALID);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = WorldUtils.getTile(world, pos);

        if (tile instanceof TileMultiblockController) {
            return getDefaultState().withProperty(DIRECTION, ((TileMultiblockController) tile).getDirection()).withProperty(VALID, ((TileMultiblockController) tile).isValid());
        }

        return state;
    }

    @Override
    public boolean isIntermediate() {
        return true;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(VALID, false));
    }
}
