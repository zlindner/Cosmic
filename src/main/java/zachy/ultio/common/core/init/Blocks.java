package zachy.ultio.common.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import zachy.ultio.common.block.BlockCable;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.item.ItemBlockBase;
import zachy.ultio.common.tile.TileCable;

@Mod.EventBusSubscriber
public class Blocks {

    public static Block cable = new BlockCable();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        r.register(cable);

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileCable.class, Lib.MOD_ID + ":" + Lib.Blocks.CABLE);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(new ItemBlockBase(cable, true));
    }
}
