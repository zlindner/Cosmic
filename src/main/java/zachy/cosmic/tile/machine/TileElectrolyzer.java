package zachy.cosmic.tile.machine;

import elucent.albedo.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.base.TileMachine;

import javax.annotation.Nullable;

public class TileElectrolyzer extends TileMachine {

    public TileElectrolyzer() {
        name = Lib.Blocks.ELECTROLYZER;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3};

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Override
    public double getMaxInput() {
        return 0;
    }

    @Override
    public void update() {

    }

    @Nullable
    @Override
    public Light provideLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            //if (!active) {
                //return Light.builder().pos(pos).color(31, 134, 251).radius(2).build();
            //}
        }

        return null;
    }
}
