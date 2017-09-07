package zachy.cosmic.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FluidUtils {

    @CapabilityInject(IFluidHandlerItem.class)
    public static final Capability<IFluidHandler> FLUID_HANDLER_ITEM = null;

    public static boolean isFluidHandler(ItemStack stack) {
        return !stack.isEmpty() && stack.hasCapability(FLUID_HANDLER_ITEM, null);
    }

    public static boolean drainItemToHandler(ItemStack stack, IFluidHandler handler, EntityPlayer player, EnumHand hand) {
        if (stack.isEmpty() || handler == null || player == null) {
            return false;
        }

        IItemHandler playerInv = new InvWrapper(player.inventory);
        FluidActionResult result = FluidUtil.tryEmptyContainerAndStow(stack, handler, playerInv, Integer.MAX_VALUE, player);

        if (result.isSuccess()) {
            player.setHeldItem(hand, result.getResult());
            return true;
        }

        return false;
    }
}
