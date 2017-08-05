package zachy.cosmic.client.gui;

import zachy.cosmic.common.container.ContainerBlastFurnace;
import zachy.cosmic.common.tile.TileBlastFurnace;

public class GuiBlastFurnace extends GuiBase {

    private TileBlastFurnace tile;

    public GuiBlastFurnace(ContainerBlastFurnace container) {
        super(container, 176, 182);

        tile = (TileBlastFurnace) container.getTile();
    }

    @Override
    public void init(int x, int y) {

    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/blast_furnace.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isWorking()) {
            drawTexture(x + 86, y + 43, 176, 0, getProgressScaled(22), 15);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawStringCentred(xSize, 5, format("gui.cosmic:blast_furnace"));

        if (tile.isValid()) {
            drawString(105, 90, format("gui.cosmic:blast_furnace.heat") + ": " + tile.getHeat() + " K");
        } else {
            drawString(80, 90, format("gui.cosmic:invalid"));
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
