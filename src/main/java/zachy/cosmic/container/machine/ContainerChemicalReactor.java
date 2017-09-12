package zachy.cosmic.container.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.container.slot.SlotOutput;
import zachy.cosmic.tile.machine.TileChemicalReactor;

public class ContainerChemicalReactor extends ContainerBase {

    public ContainerChemicalReactor(TileChemicalReactor tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 71, 24));
        addSlotToContainer(new Slot(tile, 1, 89, 24));

        addSlotToContainer(new SlotOutput(tile, 2, 80, 60));

        addPlayerInventory(8, 100);
    }
}
