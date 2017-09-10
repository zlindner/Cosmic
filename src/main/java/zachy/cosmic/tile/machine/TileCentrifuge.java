package zachy.cosmic.tile.machine;

import com.elytradev.mirage.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.base.TileMachine;

import javax.annotation.Nullable;

public class TileCentrifuge extends TileMachine {

    public TileCentrifuge() {
        name = Lib.Blocks.CENTRIFUGE;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3, 4, 5};

        maxInput = 128;
        maxStored = 10000;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Light getColoredLight() {
        return null;
    }
}
