package zachy.cosmic.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zachy.cosmic.client.gui.*;
import zachy.cosmic.client.gui.machine.GuiCentrifuge;
import zachy.cosmic.client.gui.machine.GuiChemicalReactor;
import zachy.cosmic.client.gui.machine.GuiElectrolyzer;
import zachy.cosmic.client.gui.multiblock.*;
import zachy.cosmic.container.machine.ContainerCentrifuge;
import zachy.cosmic.container.machine.ContainerChemicalReactor;
import zachy.cosmic.container.machine.ContainerElectrolyzer;
import zachy.cosmic.container.multiblock.*;
import zachy.cosmic.tile.machine.TileCentrifuge;
import zachy.cosmic.tile.machine.TileChemicalReactor;
import zachy.cosmic.tile.machine.TileElectrolyzer;
import zachy.cosmic.tile.multiblock.*;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    private Container getContainer(int ID, TileEntity tile, EntityPlayer player) {
        if (ID == Guis.BLAST_FURNACE.ID()) {
            return new ContainerBlastFurnace((TileBlastFurnace) tile, player);
        } else if (ID == Guis.CENTRIFUGE.ID()) {
            return new ContainerCentrifuge((TileCentrifuge) tile, player);
        } else if (ID == Guis.CHEMICAL_REACTOR.ID()) {
            return new ContainerChemicalReactor((TileChemicalReactor) tile, player);
        } else if (ID == Guis.COMPRESSOR.ID()) {
            return new ContainerCompressor((TileCompressor) tile, player);
        } else if (ID == Guis.DISTILLATION_TOWER.ID()) {
            return new ContainerDistillationTower((TileDistillationTower) tile, player);
        } else if (ID == Guis.ELECTROLYZER.ID()) {
            return new ContainerElectrolyzer((TileElectrolyzer) tile, player);
        } else if (ID == Guis.GRINDER.ID()) {
            return new ContainerGrinder((TileGrinder) tile, player);
        } else if (ID == Guis.SAWMILL.ID()) {
            return new ContainerSawmill((TileSawmill) tile, player);
        } else if (ID == Guis.VACUUM_FREEZER.ID()) {
            return new ContainerVacuumFreezer((TileVacuumFreezer) tile, player);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Guis.BLAST_FURNACE.ID()) {
            return new GuiBlastFurnace((ContainerBlastFurnace) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.CENTRIFUGE.ID()) {
            return new GuiCentrifuge((ContainerCentrifuge) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.CHEMICAL_REACTOR.ID()) {
            return new GuiChemicalReactor((ContainerChemicalReactor) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.COMPRESSOR.ID()) {
            return new GuiCompressor((ContainerCompressor) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.DISTILLATION_TOWER.ID()) {
            return new GuiDistillationTower((ContainerDistillationTower) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.ELECTROLYZER.ID()) {
            return new GuiElectrolyzer((ContainerElectrolyzer) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.GRINDER.ID()) {
            return new GuiIGrinder((ContainerGrinder) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.SAWMILL.ID()) {
            return new GuiISawmill((ContainerSawmill) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        } else if (ID == Guis.VACUUM_FREEZER.ID()) {
            return new GuiVacuumFreezer((ContainerVacuumFreezer) getContainer(ID, world.getTileEntity(new BlockPos(x, y, z)), player));
        }

        return null;
    }
}
