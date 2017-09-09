package zachy.cosmic.block.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.Cosmic;
import zachy.cosmic.block.base.BlockMachine;
import zachy.cosmic.client.gui.Guis;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.machine.TileElectrolyzer;

public class BlockElectrolyzer extends BlockMachine {

    public BlockElectrolyzer() {
        super(Lib.Blocks.ELECTROLYZER);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileElectrolyzer();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(Cosmic.INSTANCE, Guis.ELECTROLYZER, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
}
