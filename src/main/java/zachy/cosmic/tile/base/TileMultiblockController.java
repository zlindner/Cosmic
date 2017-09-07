package zachy.cosmic.tile.base;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.Cosmic;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.util.StackUtils;

public abstract class TileMultiblockController extends TileMachine {

    protected boolean valid = false;
    protected int counter = 0;
    protected int heat = 0;

    protected abstract boolean verifyStructure();

    public boolean isValid() {
        return valid;
    }

    @Override
    public double getMaxStored() {
        return 10000;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        counter++;

        if (counter == 20) {
            valid = verifyStructure();

            sync();

            counter = 0;
        }

        if (!valid) {
            return;
        }

        if (energy < 0) {
            return;
        }

        if (recipe != null) {
            if (recipe.getHeat() != 0 && recipe.getHeat() > heat) {
                return;
            }

            if (tank != null && recipe.getFluidAmount() > tank.getFluidAmount()) {
                System.out.println("return");

                return;
            }
        }

        if (working) {
            if (recipe == null) {
                working = false;
            } else {
                // Check if there is space for outputs
                int emptyOutputs = 0;
                int stackableOutputs = 0;

                for (int i = 0; i < getOutputs(); i++) {
                    ItemStack outputStack = getStackInSlot(OUTPUT_SLOTS[i]);
                    ItemStack recipeStack = recipe.getOutput(i);

                    if (outputStack.isEmpty()) {
                        emptyOutputs++;
                    } else if (StackUtils.isCraftingEquivalent(recipeStack, outputStack, true)) {
                        if (recipeStack.getCount() + outputStack.getCount() <= outputStack.getMaxStackSize()) {
                            stackableOutputs++;
                        }
                    }
                }

                if (emptyOutputs + stackableOutputs == getOutputs()) {
                    // There is space for outputs, check for energy requirements
                    if (energy >= recipe.getEnergy()) {
                        drainEnergy(recipe.getEnergy());
                    } else {
                        progress = 0;

                        return;
                    }

                    progress++;
                }

                if (progress >= recipe.getDuration()) {
                    // Remove inputs
                    for (int i = 0; i < getInputs(); i++) {
                        ItemStack stack = getStackInSlot(INPUT_SLOTS[i]);

                        if (!stack.isEmpty()) {
                            stack.shrink(recipe.getInput(i).getCount());
                        }
                    }

                    // Remove outputs
                    for (int i = 0; i < getOutputs(); i++) {
                        ItemStack stack = getStackInSlot(OUTPUT_SLOTS[i]);

                        if (stack.isEmpty()) {
                            setInventorySlotContents(OUTPUT_SLOTS[i], recipe.getOutput(i).copy());
                        } else {
                            stack.grow(recipe.getOutput(i).getCount());

                            sync();
                        }
                    }

                    // Remove fluid from tank if applicable
                    if (tank != null && recipe.getFluidAmount() > 0) {
                        tank.drain(recipe.getFluidAmount(), true);
                    }

                    // Check to see if the recipe is still craftable
                    if (tank != null) {
                        recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs(), tank);
                    } else {
                        recipe = API.instance().getMachineRegistry(name).getRecipe(this, getInputs());
                    }

                    progress = 0;
                }

                sync();
            }
        } else if (recipe != null) {
            working = true;
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        boolean clientValid = valid;

        super.onDataPacket(net, packet);

        if (world.isRemote) {
            boolean serverValid = valid;

            if (serverValid != clientValid) {
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        valid = tag.getBoolean(Lib.NBT.VALID);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setBoolean(Lib.NBT.VALID, valid);

        return super.writeToNBT(tag);
    }

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (valid) {
                return Light.builder().pos(pos.offset(getDirection())).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos.offset(getDirection())).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
