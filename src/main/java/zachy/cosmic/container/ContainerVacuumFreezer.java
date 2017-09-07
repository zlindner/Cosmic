package zachy.cosmic.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.multiblock.TileVacuumFreezer;

public class ContainerVacuumFreezer extends ContainerBase {

    public ContainerVacuumFreezer(TileVacuumFreezer tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 66, 42));

        addSlotToContainer(new SlotOutput(tile, 1, 120, 42));

        addPlayerInventory(8, 100);
    }
}
