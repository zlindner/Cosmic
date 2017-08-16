package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileSawmill;

public class ContainerSawmill extends ContainerBase {

    public ContainerSawmill(TileSawmill tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 57, 18));

        addSlotToContainer(new SlotOutput(tile, 1, 112, 43));
        addSlotToContainer(new SlotOutput(tile, 2, 130, 43));

        addPlayerInventory(8, 100);
    }
}
