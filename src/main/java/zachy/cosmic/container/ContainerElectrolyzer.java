package zachy.cosmic.container;

import net.minecraft.entity.player.EntityPlayer;
import zachy.cosmic.tile.machine.TileElectrolyzer;

public class ContainerElectrolyzer extends ContainerBase {

    public ContainerElectrolyzer(TileElectrolyzer tile, EntityPlayer player) {
        super(tile, player);

        addPlayerInventory(8, 100);
    }
}
