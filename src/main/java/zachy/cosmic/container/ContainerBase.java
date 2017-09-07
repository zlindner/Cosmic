package zachy.cosmic.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import zachy.cosmic.tile.base.TileBase;

public class ContainerBase extends Container {

    private TileBase tile;
    private EntityPlayer player;

    private int inputs;
    private int outputs;

    public ContainerBase(TileBase tile, EntityPlayer player) {
        this.tile = tile;
        this.player = player;

        inputs = tile.getInputs();
        outputs = tile.getOutputs();
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public TileBase getTile() {
        return tile;
    }

    protected void addPlayerInventory(int xInventory, int yInventory) {
        int id = 0;

        for (int i = 0; i < 9; i++) {
            int x = xInventory + i * 18;
            int y = yInventory + 4 + (3 * 18);

            addSlotToContainer(new Slot(player.inventory, id, x, y));

            id++;
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(player.inventory, id, xInventory + x * 18, yInventory + y * 18));

                id++;
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;

        Slot slot = getSlot(index);

        if (slot.getHasStack()) {
            stack = slot.getStack();

            if (index < inputs + outputs) {
                if (!mergeItemStack(stack, inputs + outputs + 9, inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stack, 0, inputs, false)) {
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

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
