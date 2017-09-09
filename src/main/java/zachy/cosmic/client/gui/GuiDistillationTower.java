package zachy.cosmic.client.gui;

import zachy.cosmic.container.multiblock.ContainerDistillationTower;
import zachy.cosmic.tile.multiblock.TileDistillationTower;

public class GuiDistillationTower extends GuiBase {

    private TileDistillationTower tile;

    public GuiDistillationTower(ContainerDistillationTower container) {
        super(container, 176, 182);

        tile = (TileDistillationTower) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/distillation_tower.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 94, y + 23, 176, 0, getProgressScaled(22), 54);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:distillation_tower"));

        if (!tile.isValid()) {
            drawString(80, 90, format("gui.cosmic:invalid"));
        }

        drawString(8, 90, format("container.inventory"));
    }
}
