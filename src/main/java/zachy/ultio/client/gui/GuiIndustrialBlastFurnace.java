package zachy.ultio.client.gui;

import zachy.ultio.common.container.ContainerIndustrialBlastFurnace;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

public class GuiIndustrialBlastFurnace extends GuiBase {

    TileIndustrialBlastFurnace tile;

    public GuiIndustrialBlastFurnace(ContainerIndustrialBlastFurnace container) {
        super(container, 176, 166);

        tile = (TileIndustrialBlastFurnace) container.getTile();
    }

    @Override
    public void init(int x, int y) {

    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/industrial_blast_furnace.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        /*if (TileIndustrialBlastFurnace.WORKING.getValue()) {
            drawTexture(x + 83, y + 38 - 1, 212, 0, getProgressScaled(22), 15);
        }*/
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawString(7, 7, format("gui.ultio:industrial_blast_furnace"));

        if (tile.valid) {
            drawString(7, 67, format("gui.ultio:industrial_blast_furnace.valid"));
        } else {
            drawString(7, 67, format("gui.ultio:industrial_blast_furnace.invalid"));
        }

        //heat

        drawString(7, 77, format("container.inventory"));
    }
}
