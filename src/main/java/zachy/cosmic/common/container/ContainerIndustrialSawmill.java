package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import zachy.cosmic.common.tile.base.TileBase;

public class ContainerIndustrialSawmill extends ContainerBase {

    public ContainerIndustrialSawmill(TileBase tile, EntityPlayer player) {
        super(tile, player);

        addPlayerInventory(8, 100);
    }
}
