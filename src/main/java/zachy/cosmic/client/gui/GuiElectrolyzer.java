package zachy.cosmic.client.gui;

import zachy.cosmic.container.ContainerElectrolyzer;
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
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:electrolyzer"));

        drawString(8, 90, format("container.inventory"));
    }
}
