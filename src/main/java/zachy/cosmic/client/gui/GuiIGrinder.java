package zachy.cosmic.client.gui;

import zachy.cosmic.common.container.ContainerGrinder;
import zachy.cosmic.common.core.util.RenderUtils;
import zachy.cosmic.common.tile.TileGrinder;

public class GuiIGrinder extends GuiBase {

    private TileGrinder tile;

    private final RenderUtils.FluidRenderer FLUID_RENDERER = new RenderUtils.FluidRenderer(16000, 12, 38);

    public GuiIGrinder(ContainerGrinder container) {
        super(container, 176, 182);

        tile = (TileGrinder) container.getTile();
    }

    @Override
    public void init(int x, int y) {

    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/grinder.png");

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
        drawStringCentred(xSize, 5, format("gui.cosmic:grinder"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:invalid"));
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
