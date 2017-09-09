package zachy.cosmic.tile.base;

import com.elytradev.mirage.lighting.Light;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.ArrayUtils;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;

public abstract class TileMultiblockController extends TileMachine {

    protected int counter;
    protected boolean valid;
    protected boolean alternateOrientation;

    protected abstract boolean valid();

    public boolean isValid() {
        return valid;
    }

    @Override
    public void update() {
        counter++;

        if (counter == 20) {
            valid = valid();

            sync();

            counter = 0;
        }

        if (!valid) {
            return;
        }

        super.update();
    }

    @Override
    public double getMaxStored() {
        return 10000;
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

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (alternateOrientation) {
            if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
                return new int[0];
            }

            return ArrayUtils.addAll(INPUT_SLOTS, OUTPUT_SLOTS);
        }

        if (side == getDirection() || side == getDirection().getOpposite()) {
            return new int[0];
        }

        return ArrayUtils.addAll(INPUT_SLOTS, OUTPUT_SLOTS);
    }

    @Optional.Method(modid = "mirage")
    @Override
    public Light getColoredLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (valid) {
                if (alternateOrientation) {
                    return Light.builder().pos(pos).color(0, 1, 0).radius(2).build();
                }

                return Light.builder().pos(pos.offset(getDirection())).color(0, 1, 0).radius(2).build();
            }

            if (alternateOrientation) {
                return Light.builder().pos(pos).color(1, 0, 0).radius(2).build();
            }

            return Light.builder().pos(pos.offset(getDirection())).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
