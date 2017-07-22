package zachy.ultio.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.ultio.common.core.Lib;

import javax.annotation.Nullable;

public class TileBase extends TileEntity {

    private EnumFacing direction = EnumFacing.NORTH;

    protected int counter = 0;

    public void setDirection(EnumFacing direction) {
        this.direction = direction;

        world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);

        markDirty();
    }

    public EnumFacing getDirection() {
        return direction;
    }

    public NBTTagCompound write(NBTTagCompound tag) {
        tag.setInteger(Lib.NBT.DIRECTION, direction.ordinal());

        return tag;
    }

    public NBTTagCompound writeUpdate(NBTTagCompound tag) {
        tag.setInteger(Lib.NBT.DIRECTION, direction.ordinal());

        return tag;
    }

    public void read(NBTTagCompound tag) {
        direction = EnumFacing.getFront(tag.getInteger(Lib.NBT.DIRECTION));
    }

    public void readUpdate(NBTTagCompound tag) {
        boolean doRender = canCauseRenderUpdate(tag);

        direction = EnumFacing.getFront(tag.getInteger(Lib.NBT.DIRECTION));

        if (doRender) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    protected boolean canCauseRenderUpdate(NBTTagCompound tag) {
        return true;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeUpdate(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readUpdate(packet.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.readFromNBT(tag);

        readUpdate(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        read(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        return write(super.writeToNBT(tag));
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
