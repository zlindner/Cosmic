package zachy.ultio.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zachy.ultio.apiimpl.recipe.BlastFurnaceRecipeLoader;
import zachy.ultio.client.core.handler.GuiHandler;
import zachy.ultio.common.Ultio;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Ultio.INSTANCE, new GuiHandler());

        BlastFurnaceRecipeLoader.load();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
