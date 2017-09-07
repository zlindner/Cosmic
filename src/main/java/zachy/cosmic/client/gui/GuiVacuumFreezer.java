package zachy.cosmic.client.gui;

import zachy.cosmic.container.ContainerVacuumFreezer;
import zachy.cosmic.tile.multiblock.TileVacuumFreezer;

public class GuiVacuumFreezer extends GuiBase {

    private TileVacuumFreezer tile;

    public GuiVacuumFreezer(ContainerVacuumFreezer container) {
        super(container, 176, 182);

        tile = (TileVacuumFreezer) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/vacuum_freezer.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isWorking()) {
            drawTexture(x + 91, y + 45, 176, 0, getProgressScaled(22), 10);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:vacuum_freezer"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:invalid"));
        }

        drawString(8, 90, format("container.inventory"));
    }
}
