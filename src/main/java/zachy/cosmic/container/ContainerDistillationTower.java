package zachy.cosmic.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.multiblock.TileDistillationTower;

public class ContainerDistillationTower extends ContainerBase {

    public ContainerDistillationTower(TileDistillationTower tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 74, 42));
        addSlotToContainer(new Slot(tile, 1, 74, 60));

        addSlotToContainer(new SlotOutput(tile, 2, 114, 24));
        addSlotToContainer(new SlotOutput(tile, 3, 114, 42));
        addSlotToContainer(new SlotOutput(tile, 4, 114, 60));

        addPlayerInventory(8, 100);
    }
}
