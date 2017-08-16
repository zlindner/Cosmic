package zachy.cosmic.apiimpl.recipe.sawmill;

public class SawmillRegistry {

   /* @Nullable
    @Override
    public ISawmillRecipe getRecipe(IInventory inventory, FluidTank tank) {
        for (ISawmillRecipe recipe : recipes) {
            boolean itemFound = false;
            boolean fluidFound = false;

            NonNullList<ItemStack> possibilities = recipe.getInput();

            if (possibilities.isEmpty() && inventory.getStackInSlot(0).isEmpty()) {
                itemFound = true;
            }

            FluidStack fluid = recipe.getFluid();

            if (fluid == null && tank.getFluid() == null) {
                fluidFound = true;
            }

            if (itemFound && fluidFound) {
                return recipe;
            }

            for (ItemStack possibility : possibilities) {
                if (API.instance().getComparer().isEqual(possibility, inventory.getStackInSlot(0), IComparer.COMPARE_NBT | IComparer.COMPARE_DAMAGE | IComparer.COMPARE_STRIP_NBT)) {
                    if (inventory.getStackInSlot(0).getCount() >= possibility.getCount()) {
                        itemFound = true;

                        break;
                    }
                }
            }

            if (fluid.isFluidEqual(tank.getFluid())) {
                fluidFound = true;
            }

            if (itemFound && fluidFound) {
                return recipe;
            }
        }

        return null;
    }*/
}
