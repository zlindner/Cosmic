package zachy.cosmic.integration.jei;

import mezz.jei.api.gui.IDrawableAnimated;
import net.minecraft.util.ResourceLocation;
import zachy.cosmic.apiimpl.state.enums.MachineType;
import zachy.cosmic.core.Lib;

// TODO idk this is so ugly rip, find a better way to pass the integers
// chemical reactor click bounds 73, 45, 30, 10
public enum JEIMachine {
    CHEMICAL_REACTOR(MachineType.CHEMICAL_REACTOR, 70, 23, 36, 54, 176, 0, 39, 9, new int[]{0, 18, 9}, new int[]{0, 0, 36}, IDrawableAnimated.StartDirection.TOP);

    MachineType machine;
    int backgroundBounds[];
    int progressBounds[];
    int iX[];
    int iY[];
    IDrawableAnimated.StartDirection direction;
    ResourceLocation resourceLocation;

    JEIMachine(MachineType machine, int u1, int v1, int w1, int h1, int u2, int v2, int w2, int h2, int iX[], int iY[], IDrawableAnimated.StartDirection direction) {
        this.machine = machine;
        backgroundBounds = new int[]{u1, v1, w1, h1};
        progressBounds = new int[]{u2, v2, w2, h2};
        this.iX = iX;
        this.iY = iY;
        this.direction = direction;

        resourceLocation = new ResourceLocation(Lib.MOD_ID, "textures/gui/" + getName() + ".png");
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getBackgroundBound(int index) {
        return backgroundBounds[index];
    }

    public int getProgressBound(int index) {
        return progressBounds[index];
    }

    public int getInputs() {
        return machine.getInputs();
    }

    public int getOutputs() {
        return machine.getOutputs();
    }

    public int getIngredientX(int index) {
        return iX[index];
    }

    public int getIngredientY(int index) {
        return iY[index];
    }

    public IDrawableAnimated.StartDirection getDirection() {
        return direction;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }
}
