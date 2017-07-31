package zachy.cosmic.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import zachy.cosmic.common.container.slot.SlotOutput;
import zachy.cosmic.common.tile.TileIndustrialGrinder;

public class ContainerIndustrialGrinder extends ContainerBase {

    public ContainerIndustrialGrinder(TileIndustrialGrinder tile, EntityPlayer player) {
        super(tile, player);

        addSlotToContainer(new Slot(tile, 0, 38, 34));

        addSlotToContainer(new SlotOutput(tile, 1, 93, 43));
        addSlotToContainer(new SlotOutput(tile, 2, 111, 43));
        addSlotToContainer(new SlotOutput(tile, 3, 129, 43));
        addSlotToContainer(new SlotOutput(tile, 4, 147, 43));

        addPlayerInventory(8, 100);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;

        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            stack = slot.getStack();

            if (index < 5) {
                if (!mergeItemStack(stack, 5 + 9, inventorySlots.size(), false)) {
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
