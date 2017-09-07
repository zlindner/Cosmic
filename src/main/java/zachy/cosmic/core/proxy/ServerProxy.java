package zachy.cosmic.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zachy.cosmic.apiimpl.recipe.MachineRecipeLoader;
import zachy.cosmic.apiimpl.state.enums.MachineType;
import zachy.cosmic.core.handler.GuiHandler;
import zachy.cosmic.Cosmic;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Cosmic.INSTANCE, new GuiHandler());

        for (MachineType machine : MachineType.values()) {
            MachineRecipeLoader.load(machine.getName(), machine.getInputs(), machine.getOutputs());
        }
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
