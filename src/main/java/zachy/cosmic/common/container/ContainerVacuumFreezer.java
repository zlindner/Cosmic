package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileVacuumFreezer;

public class ContainerVacuumFreezer extends ContainerBase {

    public ContainerVacuumFreezer(TileVacuumFreezer tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 66, 43));

        addSlotToContainer(new SlotOutput(tile, 1, 120, 43));

        addPlayerInventory(8, 100);
    }
}
