package zachy.cosmic.client.gui;

import zachy.cosmic.common.container.ContainerIndustrialGrinder;
import zachy.cosmic.common.core.util.RenderUtils;
import zachy.cosmic.common.tile.TileIndustrialGrinder;

public class GuiIndustrialGrinder extends GuiBase {

    private TileIndustrialGrinder tile;

    private final RenderUtils.FluidRenderer FLUID_RENDERER = new RenderUtils.FluidRenderer(16000, 12, 38);

    //TODO add fluid tooltip on hover (fluid name + amount)
    public GuiIndustrialGrinder(ContainerIndustrialGrinder container) {
        super(container, 176, 182);

        tile = (TileIndustrialGrinder) container.getTile();
    }

    @Override
    public void init(int x, int y) {

    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/industrial_grinder.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isWorking()) {
            drawTexture(x + 72, y + 45, 176, 0, getProgressScaled(22), 11);
        }

        if (tile.getFluidStack() != null) {
            FLUID_RENDERER.draw(mc, x + 50, y + 43, tile.getFluidStack());
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:industrial_grinder"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:industrial_grinder.invalid"));
        }

        if (inBounds(46, 39, 20, 46, mouseX, mouseY) && tile.getFluidStack() != null) {
            drawTooltip(mouseX, mouseY, tile.getFluidStack().getLocalizedName() + "\n" + tile.getFluidStack().amount + " mB");
        }

        drawString(8, 90, format("container.inventory"));
    }

    private int getProgressScaled(int scale) {
        float progress = tile.getProgress();
        float duration = tile.getDuration();

        if (progress > duration) {
            return scale;
        }

        return (int) (progress / duration * (float) scale);
    }
}
