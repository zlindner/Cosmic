package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileGrinder;

public class ContainerGrinder extends ContainerBase {

    public ContainerGrinder(TileGrinder tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 48, 18));

        addSlotToContainer(new SlotOutput(tile, 1, 103, 43));
        addSlotToContainer(new SlotOutput(tile, 2, 121, 43));
        addSlotToContainer(new SlotOutput(tile, 3, 139, 43));

        addPlayerInventory(8, 100);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;

        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            stack = slot.getStack();

            if (index < 4) {
                if (!mergeItemStack(stack, 4 + 9, inventorySlots.size(), false)) {
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
