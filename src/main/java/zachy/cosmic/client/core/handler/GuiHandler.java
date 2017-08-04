package zachy.cosmic.client.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zachy.cosmic.client.gui.GuiIndustrialBlastFurnace;
import zachy.cosmic.client.gui.GuiIndustrialGrinder;
import zachy.cosmic.client.gui.GuiIndustrialSawmill;
import zachy.cosmic.client.gui.Guis;
import zachy.cosmic.common.container.ContainerIndustrialBlastFurnace;
import zachy.cosmic.common.container.ContainerIndustrialGrinder;
import zachy.cosmic.common.container.ContainerIndustrialSawmill;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.tile.TileIndustrialBlastFurnace;
import zachy.cosmic.common.tile.TileIndustrialGrinder;
import zachy.cosmic.common.tile.TileIndustrialSawmill;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    private Container getContainer(int Id, TileEntity tile, EntityPlayer player) {
        switch(Id) {
            case Guis.INDUSTRIAL_BLAST_FURNACE:
                return new ContainerIndustrialBlastFurnace((TileIndustrialBlastFurnace) tile, player);
            case Guis.INDUSTRIAL_GRINDER:
                return new ContainerIndustrialGrinder((TileIndustrialGrinder) tile, player);
            case Guis.INDUSTRIAL_SAWMILL:
                return new ContainerIndustrialSawmill((TileIndustrialSawmill) tile, player);
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
            case Guis.INDUSTRIAL_BLAST_FURNACE:
                return new GuiIndustrialBlastFurnace((ContainerIndustrialBlastFurnace) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.INDUSTRIAL_GRINDER:
                return new GuiIndustrialGrinder((ContainerIndustrialGrinder) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            case Guis.INDUSTRIAL_SAWMILL:
                return new GuiIndustrialSawmill((ContainerIndustrialSawmill) getContainer(Id, WorldUtils.getTile(world, new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }
}
