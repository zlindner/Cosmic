package zachy.cosmic.client.gui;

import zachy.cosmic.container.ContainerCompressor;
import zachy.cosmic.tile.multiblock.TileCompressor;

public class GuiCompressor extends GuiBase {

    private TileCompressor tile;

    public GuiCompressor(ContainerCompressor container) {
        super(container, 176, 182);

        tile = (TileCompressor) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/compressor.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isWorking()) {
            drawTexture(x + 81, y + 44, 176, 0, getProgressScaled(22), 11);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:compressor"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:invalid"));
        }

        drawString(8, 90, format("container.inventory"));
    }
}
