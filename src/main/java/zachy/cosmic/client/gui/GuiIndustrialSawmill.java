package zachy.cosmic.client.gui;

import zachy.cosmic.common.container.ContainerIndustrialSawmill;
import zachy.cosmic.common.core.util.RenderUtils;
import zachy.cosmic.common.tile.TileIndustrialSawmill;

public class GuiIndustrialSawmill extends GuiBase {

    private TileIndustrialSawmill tile;

    private final RenderUtils.FluidRenderer FLUID_RENDERER = new RenderUtils.FluidRenderer(16000, 12, 38);

    public GuiIndustrialSawmill(ContainerIndustrialSawmill container) {
        super(container, 176, 182);

        tile = (TileIndustrialSawmill) container.getTile();
    }

    @Override
    public void init(int x, int y) {

    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {

    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:industrial_sawmill"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:industrial_sawmill.invalid"));
        }

        drawString(8, 90, format("container.inventory"));
    }
}
