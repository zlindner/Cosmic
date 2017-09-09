package zachy.cosmic.client.gui;

import zachy.cosmic.container.machine.ContainerElectrolyzer;
import zachy.cosmic.tile.machine.TileElectrolyzer;

public class GuiElectrolyzer extends GuiBase {

    private TileElectrolyzer tile;

    public GuiElectrolyzer(ContainerElectrolyzer container) {
        super(container, 176, 182);

        tile = (TileElectrolyzer) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/electrolyzer.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 73, y + 55 - getProgressScaled(10), 176, 10 - getProgressScaled(10), 30, 10);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:electrolyzer"));
        drawStringCentred(xSize, 90, format("container.inventory"));
    }
}
