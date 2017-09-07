package zachy.cosmic.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zachy.cosmic.client.gui.*;
import zachy.cosmic.container.*;
import zachy.cosmic.core.util.WorldUtils;
import zachy.cosmic.tile.multiblock.*;

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
            case Guis.DISTILLATION_TOWER:
                return new ContainerDistillationTower((TileDistillationTower) tile, player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        return getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) {
        switch (Id) {
            case Guis.BLAST_FURNACE:
                return new GuiBlastFurnace((ContainerBlastFurnace) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            case Guis.GRINDER:
                return new GuiIGrinder((ContainerGrinder) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            case Guis.SAWMILL:
                return new GuiISawmill((ContainerSawmill) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            case Guis.IMPLOSION_COMPRESSOR:
                return new GuiCompressor((ContainerCompressor) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            case Guis.VACUUM_FREEZER:
                return new GuiVacuumFreezer((ContainerVacuumFreezer) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            case Guis.DISTILLATION_TOWER:
                return new GuiDistillationTower((ContainerDistillationTower) getContainer(Id, world.getTileEntity(new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }
}
