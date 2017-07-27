package zachy.ultio.client.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zachy.ultio.client.gui.GuiIndustrialBlastFurnace;
import zachy.ultio.client.gui.Guis;
import zachy.ultio.common.container.ContainerIndustrialBlastFurnace;
import zachy.ultio.common.core.util.WorldUtils;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    private Container getContainer(int Id, TileEntity tile, EntityPlayer player) {
        switch(Id) {
            case Guis.INDUSTRIAL_BLAST_FURNACE:
                return new ContainerIndustrialBlastFurnace((TileIndustrialBlastFurnace) tile, player);
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
            default:
                return null;
        }
    }
}
