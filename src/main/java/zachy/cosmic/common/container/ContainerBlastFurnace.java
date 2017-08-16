package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileBlastFurnace;

public class ContainerBlastFurnace extends ContainerBase {

    public ContainerBlastFurnace(TileBlastFurnace tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 57, 34));
        addSlotToContainer(new Slot(tile, 1, 57, 52));

        addSlotToContainer(new SlotOutput(tile, 2, 111, 43));
        addSlotToContainer(new SlotOutput(tile, 3, 129, 43));

        addPlayerInventory(8, 100);
    }
}
