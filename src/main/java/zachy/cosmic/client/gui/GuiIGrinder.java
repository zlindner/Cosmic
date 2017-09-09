package zachy.cosmic.client.gui;

import zachy.cosmic.container.ContainerGrinder;
import zachy.cosmic.tile.multiblock.TileGrinder;

public class GuiIGrinder extends GuiBase {

    private TileGrinder tile;

    public GuiIGrinder(ContainerGrinder container) {
        super(container, 176, 182);

        tile = (TileGrinder) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/grinder.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 63, y + 45, 176, 0, getProgressScaled(22), 11);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:grinder"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:invalid"));
        }

        drawString(8, 90, format("container.inventory"));
    }
}
