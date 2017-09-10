package zachy.cosmic.container.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.machine.TileCentrifuge;

public class ContainerCentrifuge extends ContainerBase {

    public ContainerCentrifuge(TileCentrifuge tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 80, 42));
        addSlotToContainer(new Slot(tile, 1, 44, 7));

        addSlotToContainer(new SlotOutput(tile, 2, 80, 7));
        addSlotToContainer(new SlotOutput(tile, 3, 116, 42));
        addSlotToContainer(new SlotOutput(tile, 4, 80, 78));
        addSlotToContainer(new SlotOutput(tile, 5, 44, 42));

        addPlayerInventory(8, 100);
    }
}
