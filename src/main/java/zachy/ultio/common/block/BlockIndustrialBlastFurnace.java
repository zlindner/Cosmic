package zachy.ultio.common.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zachy.ultio.client.core.handler.ModelHandler;
import zachy.ultio.client.gui.Guis;
import zachy.ultio.common.Ultio;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.Util;
import zachy.ultio.common.item.ItemBlockBase;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

import java.util.Arrays;

public class BlockIndustrialBlastFurnace extends BlockBase {

    private static final PropertyDirection DIRECTION = PropertyDirection.create("direction", Arrays.asList(EnumFacing.HORIZONTALS));
    private static final PropertyBool COMPLETE = PropertyBool.create("complete");

    public BlockIndustrialBlastFurnace() {
        super(Lib.Blocks.INDUSTRIAL_BLAST_FURNACE);

        setDefaultState(getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(COMPLETE, false));
    }

    @Override
    public Item createItemBlock() {
        ItemBlockBase itemBlock = new ItemBlockBase(this);

        itemBlock.setHorizontal();

        return itemBlock;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileIndustrialBlastFurnace();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION, COMPLETE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = Util.getTile(world, pos);

        if (tile instanceof TileIndustrialBlastFurnace) {
            return getDefaultState().withProperty(DIRECTION, ((TileIndustrialBlastFurnace) tile).getDirection()).withProperty(COMPLETE, ((TileIndustrialBlastFurnace) tile).isValid());
        }

        return state;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Ultio.instance, Guis.INDUSTRIAL_BLAST_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(COMPLETE, false));
    }
}
