package zachy.cosmic.tile.machine;

import com.elytradev.mirage.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.base.TileMachine;

import javax.annotation.Nullable;

public class TileChemicalReactor extends TileMachine {

    public TileChemicalReactor() {
        name = Lib.Blocks.CHEMICAL_REACTOR;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2};

        maxInput = 32;
        maxStored = 10000;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Light getColoredLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (active) {

            }
        }

        return null;
    }
}
