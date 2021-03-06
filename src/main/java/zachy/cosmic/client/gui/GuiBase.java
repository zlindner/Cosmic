package zachy.cosmic.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import zachy.cosmic.container.ContainerBase;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.base.TileMachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class GuiBase extends GuiContainer {

    private static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    protected int screenWidth;
    protected int screenHeight;

    private TileMachine tile;

    public GuiBase(ContainerBase container, int screenWidth, int screenHeight) {
        super(container);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xSize = screenWidth;
        this.ySize = screenHeight;

        tile = (TileMachine) container.getTile();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        drawBackground(guiLeft, guiTop, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        mouseX -= guiLeft;
        mouseY -= guiTop;

        drawForeground(mouseX, mouseY);
    }

    public void bindTexture(String file) {
        String id = Lib.MOD_ID + ":" + file;

        if (!TEXTURE_CACHE.containsKey(id)) {
            TEXTURE_CACHE.put(id, new ResourceLocation(Lib.MOD_ID, "textures/" + file));
        }

        mc.getTextureManager().bindTexture(TEXTURE_CACHE.get(id));
    }

    public void drawString(int x, int y, String message, int color) {
        GlStateManager.disableLighting();

        fontRenderer.drawString(message, x, y, color);

        GlStateManager.enableLighting();
    }

    public void drawString(int x, int y, String message) {
        drawString(x, y, message, 4210752);
    }

    public void drawStringCentred(int x, int y, String message) {
        drawString(x / 2 - fontRenderer.getStringWidth(message), y, message);
    }

    public void drawTexture(int x, int y, int textureX, int textureY, int width, int height) {
        drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }

    public void drawTooltip(int x, int y, String lines) {
        GlStateManager.disableLighting();

        GuiUtils.drawHoveringText(Arrays.asList(lines.split("\n")), x, y, width - guiLeft, height, -1, fontRenderer);

        GlStateManager.enableLighting();
    }

    public static String format(String name, Object... format) {
        return I18n.format(name, format);
    }

    public abstract void drawBackground(int x, int y, int mouseX, int mouseY);

    public abstract void drawForeground(int mouseX, int mouseY);

    public boolean inBounds(int x, int y, int w, int h, int ox, int oy) {
        return ox >= x && ox <= x + w && oy >= y && oy <= y + h;
    }

    public int getProgressScaled(int scale) {
        float progress = tile.getProgress();
        float duration = tile.getDuration();

        if (progress > duration) {
            return scale;
        }

        return (int) (progress / duration * (float) scale);
    }
}
