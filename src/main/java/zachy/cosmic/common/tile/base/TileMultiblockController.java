package zachy.cosmic.common.tile.base;

import elucent.albedo.lighting.Light;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.util.WorldUtils;

public abstract class TileMultiblockController extends TileMachine {

    protected boolean valid = false;
    protected int counter = 0;

    protected abstract boolean verifyStructure();

    public boolean isValid() {
        return valid;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        counter++;

        if (counter == 20) {
            valid = verifyStructure();

            markDirty();

            WorldUtils.updateBlock(world, pos);

            counter = 0;
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        boolean clientValid = valid;

        super.onDataPacket(net, packet);

        if (world.isRemote) {
            boolean serverValid = valid;

            if (serverValid != clientValid) {
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        valid = tag.getBoolean(Lib.NBT.VALID);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setBoolean(Lib.NBT.VALID, valid);

        return super.writeToNBT(tag);
    }

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (valid) {
                return Light.builder().pos(pos.offset(getDirection())).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos.offset(getDirection())).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
