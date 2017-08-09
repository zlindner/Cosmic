package zachy.cosmic.common.tile;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.core.util.MultiBlockUtils;
import zachy.cosmic.common.tile.base.TileMultiBlockBase;

public class TileVacuumFreezer extends TileMultiBlockBase {

    private final int INPUT_SLOTS[] = {0};
    private final int OUTPUT_SLOTS[] = {1};

    public TileVacuumFreezer() {
        setValid(false);
        setWorking(false);
        setProgress(0);

        inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    }

    @Override
    protected boolean verifyStructure() {
        BlockPos start = pos.offset(EnumFacing.DOWN).offset(EnumFacing.DOWN).offset(EnumFacing.DOWN);

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos check = start.add(x, y, z);

                    if (y == 0 || y == 2) {
                        if (x == 0 && z == 0) {
                            if (!MultiBlockUtils.isAdvancedCasing(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiBlockUtils.isIntermediateCasing(world, check)) {
                                return false;
                            }
                        }
                    } else {
                        if (x == 0 && z == 0) {
                            if (!MultiBlockUtils.isAir(world, check)) {
                                return false;
                            }
                        } else if (x == 0 || z == 0) {
                            if (!MultiBlockUtils.isAdvancedCasing(world, check)) {
                                return false;
                            }
                        } else {
                            if (!MultiBlockUtils.isIntermediateCasing(world, check)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void update() {
        super.update();

        if (world.isRemote) {
            return;
        }

        if (!isValid()) {
            return;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public void onInventoryChanged() {

    }

    @Override
    public double getMaxInput() {
        return 128;
    }

    @Override
    public double getMaxStored() {
        return 12800;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return new int[]{INPUT_SLOTS[0]};
        } else if (side == EnumFacing.DOWN) {
            return new int[]{INPUT_SLOTS[0]};
        } else {
            return OUTPUT_SLOTS;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return index == INPUT_SLOTS[0];
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == OUTPUT_SLOTS[0];
    }

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (isValid()) {
                return Light.builder().pos(pos).color(0, 1, 0).radius(2).build();
            }

            return Light.builder().pos(pos).color(1, 0, 0).radius(2).build();
        }

        return null;
    }
}
