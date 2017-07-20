package zachy.ultio.common.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import zachy.ultio.common.block.BlockBase;
import zachy.ultio.common.block.BlockCable;
import zachy.ultio.common.block.BlockIndustrialBlastFurnace;
import zachy.ultio.common.block.BlockMachineCasing;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.tile.TileCable;
import zachy.ultio.common.tile.TileIndustrialBlastFurnace;

@Mod.EventBusSubscriber
public class UltioBlocks {

    public static BlockBase cable = new BlockCable();
    public static BlockBase machineCasing = new BlockMachineCasing();
    public static BlockBase industrialBlastFurnace = new BlockIndustrialBlastFurnace();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        r.register(cable);
        r.register(machineCasing);
        r.register(industrialBlastFurnace);

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileCable.class, Lib.MOD_ID + ":" + Lib.Blocks.CABLE);
        GameRegistry.registerTileEntity(TileIndustrialBlastFurnace.class, Lib.MOD_ID + ":" + Lib.Blocks.INDUSTRIAL_BLAST_FURNACE);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(cable.createItem());
        r.register(machineCasing.createItem());
        r.register(industrialBlastFurnace.createItem());
    }
}
