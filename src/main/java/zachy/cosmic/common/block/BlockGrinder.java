package zachy.cosmic.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import zachy.cosmic.client.core.handler.ModelHandler;
import zachy.cosmic.client.gui.Guis;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.block.base.BlockMultiBlockBase;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.FluidUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.TileGrinder;

public class BlockGrinder extends BlockMultiBlockBase {

    public BlockGrinder() {
        super(Lib.Blocks.GRINDER);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileGrinder();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        TileEntity tile = WorldUtils.getTile(world, pos);

        if (tile != null && FluidUtils.isFluidHandler(held) && FluidUtils.drainItemToHandler(held, (IFluidHandler) tile, player, hand)) {
            return true;
        }

        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(Cosmic.INSTANCE, Guis.GRINDER, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(VALID, false));
    }
}