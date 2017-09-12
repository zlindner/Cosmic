package zachy.cosmic.client.gui.machine;

import zachy.cosmic.client.gui.GuiBase;
import zachy.cosmic.container.machine.ContainerChemicalReactor;
import zachy.cosmic.tile.machine.TileChemicalReactor;

public class GuiChemicalReactor extends GuiBase {

    private TileChemicalReactor tile;

    public GuiChemicalReactor(ContainerChemicalReactor container) {
        super(container, 176, 182);

        tile = (TileChemicalReactor) container.getTile();
    }

    @Override
    public void drawBackground(int x, int y, int mouseX, int mouseY) {
        bindTexture("gui/chemical_reactor.png");

        drawTexture(x, y, 0, 0, screenWidth, screenHeight);

        if (tile.isActive()) {
            drawTexture(x + 73, y + 45, 176, 0, 30, getProgressScaled(10));
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        drawString(8, 5, format("gui.cosmic:chemical_reactor"));
        drawString(8, 90, format("container.inventory"));
    }
}
