package zachy.cosmic.client.gui.machine;

import zachy.cosmic.client.gui.GuiBase;
import zachy.cosmic.container.machine.ContainerCentrifuge;
import zachy.cosmic.tile.machine.TileCentrifuge;

public class GuiCentrifuge extends GuiBase {

    private TileCentrifuge tile;

    public GuiCentrifuge(ContainerCentrifuge container) {
        super(container, 176, 182);

        tile = (TileCentrifuge) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/centrifuge.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 83, y + 37 - getProgressScaled(10), 176, 10 - getProgressScaled(10), 10, 10);
            drawTexture(x + 101,y + 45, 186, 0, getProgressScaled(10), 10);
            drawTexture(x + 83, y + 63, 196, 0, 10, getProgressScaled(10));
            drawTexture(x + 75 - getProgressScaled(10), y + 45, 216 - getProgressScaled(10), 0, 10, 10);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawString(8, 5, format("gui.cosmic:centrifuge"));
        drawString(8, 90, format("container.inventory"));
    }
}
