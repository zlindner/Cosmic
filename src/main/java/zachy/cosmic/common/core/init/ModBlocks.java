package zachy.cosmic.common.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import zachy.cosmic.common.block.*;
import zachy.cosmic.common.block.base.BlockBase;
import zachy.cosmic.common.core.Lib;
import zachy.cosmic.common.tile.TileBlastFurnace;
import zachy.cosmic.common.tile.TileGrinder;
import zachy.cosmic.common.tile.TileSawmill;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static BlockBase machineCasing = new BlockMachineCasing();
    public static BlockBase machineFrame = new BlockMachineFrame();
    public static BlockBase blastFurnace = new BlockBlastFurnace();
    public static BlockBase grinder = new BlockGrinder();
    public static BlockBase sawmill = new BlockSawmill();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        r.register(machineCasing);
        r.register(machineFrame);
        r.register(blastFurnace);
        r.register(grinder);
        r.register(sawmill);

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileBlastFurnace.class, Lib.MOD_ID + ":" + Lib.Blocks.BLAST_FURNACE);
        GameRegistry.registerTileEntity(TileGrinder.class, Lib.MOD_ID + ":" + Lib.Blocks.GRINDER);
        GameRegistry.registerTileEntity(TileSawmill.class, Lib.MOD_ID + ":" + Lib.Blocks.SAWMILL);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(machineCasing.createItemBlock());
        r.register(machineFrame.createItemBlock());
        r.register(blastFurnace.createItemBlock());
        r.register(grinder.createItemBlock());
        r.register(sawmill.createItemBlock());
    }
}
