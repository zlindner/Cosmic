package zachy.cosmic.container.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.multiblock.TileGrinder;

public class ContainerGrinder extends ContainerBase {

    public ContainerGrinder(TileGrinder tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 39, 33));
        addSlotToContainer(new Slot(tile, 1, 39, 51));

        addSlotToContainer(new SlotOutput(tile, 2, 94, 42));
        addSlotToContainer(new SlotOutput(tile, 3, 112, 42));
        addSlotToContainer(new SlotOutput(tile, 4, 130, 42));
        addSlotToContainer(new SlotOutput(tile, 5, 148, 42));

        addPlayerInventory(8, 100);
    }
}
