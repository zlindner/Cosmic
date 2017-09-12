package zachy.cosmic.core.init;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import zachy.cosmic.item.ItemCell;
import zachy.cosmic.item.ItemPortableScanner;
import zachy.cosmic.item.base.ItemBase;

@Mod.EventBusSubscriber
public class ModItems {

    public static ItemBase cell = new ItemCell();
    public static ItemBase portable_scanner = new ItemPortableScanner();

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(cell);
        r.register(portable_scanner);
    }
}
