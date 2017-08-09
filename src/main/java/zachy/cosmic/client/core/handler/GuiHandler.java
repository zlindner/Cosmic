package zachy.cosmic.client.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zachy.cosmic.client.gui.*;
import zachy.cosmic.common.container.*;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.*;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    private Container getContainer(int Id, TileEntity tile, EntityPlayer player) {
        switch(Id) {
            case Guis.BLAST_FURNACE:
                return new ContainerBlastFurnace((TileBlastFurnace) tile, player);
            case Guis.GRINDER:
                return new ContainerGrinder((TileGrinder) tile, player);
            case Guis.SAWMILL:
                return new ContainerSawmill((TileSawmill) tile, player);
            case Guis.IMPLOSION_COMPRESSOR:
                return new ContainerCompressor((TileCompressor) tile, player);
            case Guis.VACUUM_FREEZER:
                return new ContainerVacuumFreezer((TileVacuumFreezer) tile, player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        return getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        switch (Id) {
            case Guis.BLAST_FURNACE:
                return new GuiBlastFurnace((ContainerBlastFurnace) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.GRINDER:
                return new GuiIGrinder((ContainerGrinder) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.SAWMILL:
                return new GuiISawmill((ContainerSawmill) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.IMPLOSION_COMPRESSOR:
                return new GuiCompressor((ContainerCompressor) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.VACUUM_FREEZER:
                return new GuiVacuumFreezer((ContainerVacuumFreezer) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }
}
