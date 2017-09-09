package zachy.cosmic.container.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.multiblock.TileSawmill;

public class ContainerSawmill extends ContainerBase {

    public ContainerSawmill(TileSawmill tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 57, 17));

        addSlotToContainer(new SlotOutput(tile, 1, 112, 42));
        addSlotToContainer(new SlotOutput(tile, 2, 130, 42));

        addPlayerInventory(8, 100);
    }
}
