package zachy.ultio.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import zachy.ultio.common.core.Lib;

import java.util.HashMap;
import java.util.Map;

public abstract class GuiBase extends GuiContainer {

    private static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    protected int screenWidth;
    protected int screenHeight;

    public GuiBase(Container container, int screenWidth, int screenHeight) {
        super(container);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xSize = screenWidth;
        this.ySize = screenHeight;
    }

    @Override
    public void initGui() {
        calcHeight();

        super.initGui();

        buttonList.clear();

        init(guiLeft, guiTop);
    }

    protected void calcHeight() {

    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        update(guiLeft, guiTop);
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

        for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = inventorySlots.inventorySlots.get(i);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        mouseX -= guiLeft;
        mouseY -= guiTop;

        drawForeground(mouseX, mouseY);
    }

    public void bindTexture(String file) {
        bindTexture(Lib.MOD_ID, file);
    }

    public void bindTexture(String base, String file) {
        String id = base + ":" + file;

        if (!TEXTURE_CACHE.containsKey(id)) {
            TEXTURE_CACHE.put(id, new ResourceLocation(base, "textures/" + file));
        }

        mc.getTextureManager().bindTexture(TEXTURE_CACHE.get(id));
    }

    public void drawString(int x, int y, String message) {
        drawString(x, y, message, 4210752);
    }

    public void drawString(int x, int y, String message, int color) {
        GlStateManager.disableLighting();

        fontRenderer.drawString(message, x, y, color);

        GlStateManager.enableLighting();
    }

    public void drawStringCentred(int x, int y, String message) {
        fontRenderer.drawString(message, x / 2 - fontRenderer.getStringWidth(message) / 2, y, 4210752);
    }

    public void drawTexture(int x, int y, int textureX, int textureY, int width, int height) {
        drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }

    public static String format(String name, Object... format) {
        return I18n.format(name, format);
    }

    public abstract void init(int x, int y);

    public abstract void update(int x, int y);

    public abstract void drawBackground(int x, int y, int mouseX, int mouseY);

    public abstract void drawForeground(int mouseX, int mouseY);

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public static int calculateOffsetOnScale(int pos, float scale) {
        float multiplier = (pos / scale);

        return (int) multiplier;
    }
}
