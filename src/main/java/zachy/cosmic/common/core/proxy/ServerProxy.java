package zachy.cosmic.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zachy.cosmic.apiimpl.recipe.MachineRecipeLoader;
import zachy.cosmic.client.core.handler.GuiHandler;
import zachy.cosmic.common.Cosmic;
import zachy.cosmic.common.core.Lib;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Cosmic.INSTANCE, new GuiHandler());

        //TODO move dis shit to enum in api MachineType or somethin MultiblockControllerType idk man, loop thru enum
        MachineRecipeLoader.load(Lib.Blocks.BLAST_FURNACE, 2, 2);
        MachineRecipeLoader.load(Lib.Blocks.COMPRESSOR, 2, 2);
        MachineRecipeLoader.load(Lib.Blocks.GRINDER, 2, 4);
        MachineRecipeLoader.load(Lib.Blocks.SAWMILL, 1, 2);
        MachineRecipeLoader.load(Lib.Blocks.VACUUM_FREEZER, 1, 1);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
