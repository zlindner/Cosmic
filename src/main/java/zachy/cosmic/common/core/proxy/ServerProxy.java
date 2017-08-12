package zachy.cosmic.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zachy.cosmic.apiimpl.recipe.blast_furnace.BlastFurnaceRecipeLoader;
import zachy.cosmic.apiimpl.recipe.compressor.CompressorRecipeLoader;
import zachy.cosmic.apiimpl.recipe.grinder.GrinderRecipeLoader;
import zachy.cosmic.apiimpl.recipe.sawmill.SawmillRecipeLoader;
import zachy.cosmic.apiimpl.recipe.vacuum_freezer.VacuumFreezerRecipeLoader;
import zachy.cosmic.client.core.handler.GuiHandler;
import zachy.cosmic.common.Cosmic;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Cosmic.INSTANCE, new GuiHandler());

        BlastFurnaceRecipeLoader.load();
        GrinderRecipeLoader.load();
        SawmillRecipeLoader.load();
        CompressorRecipeLoader.load();
        VacuumFreezerRecipeLoader.load();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
