package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileVacuumFreezer;

public class ContainerVacuumFreezer extends ContainerBase {

    public ContainerVacuumFreezer(TileVacuumFreezer tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 66, 43));

        addSlotToContainer(new SlotOutput(tile, 1, 120, 43));

        addPlayerInventory(8, 100);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;

        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            stack = slot.getStack();

            if (index < 2) {
                if (!mergeItemStack(stack, 2 + 9, inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return stack;
    }
}
