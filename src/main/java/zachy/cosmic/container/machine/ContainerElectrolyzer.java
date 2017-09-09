package zachy.cosmic.container.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.machine.TileElectrolyzer;

public class ContainerElectrolyzer extends ContainerBase {

    public ContainerElectrolyzer(TileElectrolyzer tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 71, 67));
        addSlotToContainer(new Slot(tile, 1, 89, 67));

        addSlotToContainer(new SlotOutput(tile, 2, 53, 16));
        addSlotToContainer(new SlotOutput(tile, 3, 71, 16));
        addSlotToContainer(new SlotOutput(tile, 4, 89, 16));
        addSlotToContainer(new SlotOutput(tile, 5, 107, 16));

        addPlayerInventory(8, 100);
    }
}
