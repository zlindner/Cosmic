package zachy.ultio.client.core.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zachy.ultio.common.block.CableType;
import zachy.ultio.common.core.init.Blocks;
import zachy.ultio.common.core.proxy.IProxy;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        for (CableType cables : CableType.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.cable), cables.getId(), new ModelResourceLocation("ultio:cable", "type=" + cables.getName()));
        }
    }
}
