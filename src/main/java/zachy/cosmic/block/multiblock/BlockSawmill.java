package zachy.cosmic.block.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import zachy.cosmic.client.gui.Guis;
import zachy.cosmic.Cosmic;
import zachy.cosmic.block.base.BlockMultiblockController;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.FluidUtils;
import zachy.cosmic.tile.multiblock.TileSawmill;

public class BlockSawmill extends BlockMultiblockController {

    public BlockSawmill() {
        super(Lib.Blocks.SAWMILL);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSawmill();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && FluidUtils.isFluidHandler(held) && FluidUtils.drainItemToHandler(held, (IFluidHandler) tile, player, hand)) {
            return true;
        }

        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(Cosmic.INSTANCE, Guis.SAWMILL, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
}
