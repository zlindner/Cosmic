package zachy.cosmic.client.gui.multiblock;

import zachy.cosmic.client.gui.GuiBase;
import zachy.cosmic.container.multiblock.ContainerBlastFurnace;
import zachy.cosmic.tile.multiblock.TileBlastFurnace;

public class GuiBlastFurnace extends GuiBase {

    private TileBlastFurnace tile;

    public GuiBlastFurnace(ContainerBlastFurnace container) {
        super(container, 176, 182);

        tile = (TileBlastFurnace) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/blast_furnace.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 81, y + 42, 176, 0, getProgressScaled(22), 15);
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
}
