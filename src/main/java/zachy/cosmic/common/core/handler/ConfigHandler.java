package zachy.cosmic.common.core.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zachy.cosmic.common.core.Lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    private Configuration config;

    public boolean enableColouredLights;

    public static final String COMPATIBILITY = "compatibility";

    public ConfigHandler(File configFile) {
        config = new Configuration(configFile);

        MinecraftForge.EVENT_BUS.register(this);

        loadConfig();
    }

    public Configuration getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Lib.MOD_ID)) {
            loadConfig();
        }
    }

    private void loadConfig() {
        enableColouredLights = config.getBoolean("enableColouredLights", COMPATIBILITY, false, "Enable the ability of some blocks to emit coloured lights. Albedo must be installed.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SuppressWarnings("unchecked")
    public List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        list.addAll(new ConfigElement(config.getCategory(COMPATIBILITY)).getChildElements());

        return list;
    }
}
