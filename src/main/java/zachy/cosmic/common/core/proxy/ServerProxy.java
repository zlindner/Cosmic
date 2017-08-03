package zachy.cosmic.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zachy.cosmic.apiimpl.recipe.BlastFurnaceRecipeLoader;
import zachy.cosmic.apiimpl.recipe.GrinderRecipeLoader;
import zachy.cosmic.client.core.handler.GuiHandler;
import zachy.cosmic.common.Cosmic;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Cosmic.INSTANCE, new GuiHandler());

        BlastFurnaceRecipeLoader.load();
        GrinderRecipeLoader.load();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
