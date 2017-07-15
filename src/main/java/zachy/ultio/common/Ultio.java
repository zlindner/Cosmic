package zachy.ultio.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.proxy.IProxy;

@Mod(modid = Lib.MOD_ID, name = Lib.MOD_NAME, version = Lib.VERSION)
public class Ultio {

    @Mod.Instance(Lib.MOD_ID)
    public static Ultio instance;

    @SidedProxy(serverSide = Lib.PROXY_SERVER, clientSide = Lib.PROXY_CLIENT)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
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
