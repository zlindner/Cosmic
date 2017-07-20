package zachy.ultio.common.container;

import net.minecraft.entity.player.EntityPlayer;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

public class ContainerIndustrialBlastFurnace extends ContainerBase {

    public ContainerIndustrialBlastFurnace(TileIndustrialBlastFurnace industrialBlastFurnace, EntityPlayer player) {
        super(industrialBlastFurnace, player);

        int x = 44;
        int y = 20;

        /*for (int i = 0; i < 3; ++i) {
            addSlotToContainer(new SlotItemHandler(solderer.getNode().getIngredients(), i, x, y));

            y += 18;
        }

        addSlotToContainer(new SlotOutput(solderer.getNode().getResult(), 0, 127, 38));

        for (int i = 0; i < 4; ++i) {
            addSlotToContainer(new SlotItemHandler(solderer.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }*/

        addPlayerInventory(8, 89);
    }

    /*@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;

        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            stack = slot.getStack();

            if (index < 4) {
                if (!mergeItemStack(stack, 4 + 4, inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 4 + 4) {
                if (!mergeItemStack(stack, 4 + 4, inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack.getItem() != RSItems.UPGRADE || !mergeItemStack(stack, 4, 4 + 4, false)) {
                    if (!mergeItemStack(stack, 0, 3, false)) { // 0 - 3 because we can't shift click to output slot
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return stack;
    }*/
}
