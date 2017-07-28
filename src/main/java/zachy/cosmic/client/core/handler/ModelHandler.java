package zachy.cosmic.client.core.handler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IRegistryDelegate;
import zachy.cosmic.client.core.IModelRegister;
import zachy.cosmic.common.core.Lib;

import java.util.Map;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelHandler {

    private static final Map<IRegistryDelegate<Block>, IStateMapper> customStateMappers = ReflectionHelper.getPrivateValue(ModelLoader.class, null, "customStateMappers");
    private static final DefaultStateMapper fallbackMapper = new DefaultStateMapper();

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(Lib.MOD_ID);

        for (Block block : Block.REGISTRY) {
            if (block instanceof IModelRegister)
                ((IModelRegister) block).registerModel();
        }

        for (Item item : Item.REGISTRY) {
            if (item instanceof IModelRegister)
                ((IModelRegister) item).registerModel();
        }
    }

    private static ModelResourceLocation getResourceLocation(IBlockState state) {
        return customStateMappers.getOrDefault(state.getBlock().delegate, fallbackMapper).putStateModelLocations(state.getBlock()).get(state);
    }

    public static void registerBlock(Block block, int meta, IBlockState state) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, getResourceLocation(state));
    }
}
