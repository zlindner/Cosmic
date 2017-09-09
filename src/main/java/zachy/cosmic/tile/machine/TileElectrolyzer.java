package zachy.cosmic.tile.machine;

import com.elytradev.mirage.lighting.Light;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Optional;
import zachy.cosmic.Cosmic;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.base.TileMachine;

public class TileElectrolyzer extends TileMachine {

    public TileElectrolyzer() {
        name = Lib.Blocks.ELECTROLYZER;

        INPUT_SLOTS = new int[]{0, 1};
        OUTPUT_SLOTS = new int[]{2, 3, 4, 5};

        maxInput = 128;
        maxStored = 10000;

        inventory = NonNullList.withSize(getInputs() + getOutputs(), ItemStack.EMPTY);
    }

    @Optional.Method(modid = "mirage")
    @Override
    public Light getColoredLight() {
        if (Cosmic.INSTANCE.config.enableColouredLights) {
            if (active) {
                return Light.builder().pos(pos).color(0, 0, 1).radius(2).build();
            }
        }

        return null;
    }
}
