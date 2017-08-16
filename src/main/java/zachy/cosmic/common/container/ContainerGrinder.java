package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileGrinder;

public class ContainerGrinder extends ContainerBase {

    public ContainerGrinder(TileGrinder tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 39, 34));
        addSlotToContainer(new Slot(tile, 1, 39, 52));

        addSlotToContainer(new SlotOutput(tile, 2, 94, 43));
        addSlotToContainer(new SlotOutput(tile, 3, 112, 43));
        addSlotToContainer(new SlotOutput(tile, 4, 130, 43));
        addSlotToContainer(new SlotOutput(tile, 5, 148, 43));

        addPlayerInventory(8, 100);
    }
}
