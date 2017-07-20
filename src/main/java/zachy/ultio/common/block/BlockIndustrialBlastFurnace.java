package zachy.ultio.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.ultio.client.core.handler.ModelHandler;
import zachy.ultio.client.gui.Guis;
import zachy.ultio.common.Ultio;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

import javax.annotation.Nullable;

public class BlockIndustrialBlastFurnace extends BlockBase {

    public BlockIndustrialBlastFurnace() {
        super(Lib.Blocks.INDUSTRIAL_BLAST_FURNACE);

        setDefaultState(getDefaultState());
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Ultio.instance, Guis.INDUSTRIAL_BLAST_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Nullable
    public Direction getDirection() {
        return Direction.HORIZONTAL;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(getDirection().getProperty(), EnumFacing.NORTH));
    }
}
