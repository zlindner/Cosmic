package zachy.cosmic.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.core.handler.ConfigHandler;
import zachy.cosmic.common.core.proxy.ServerProxy;

//TODO rewrite after multiblocks 100%
@Mod(modid = Lib.MOD_ID, name = Lib.MOD_NAME, version = Lib.VERSION)
public class Cosmic {

    @Mod.Instance(Lib.MOD_ID)
    public static Cosmic INSTANCE;

    @SidedProxy(serverSide = Lib.PROXY_SERVER, clientSide = Lib.PROXY_CLIENT)
    public static ServerProxy proxy;

    public ConfigHandler config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ConfigHandler(event.getSuggestedConfigurationFile());

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
