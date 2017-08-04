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
import zachy.cosmic.common.tile.TileIndustrialBlastFurnace;
import zachy.cosmic.common.tile.TileIndustrialGrinder;
import zachy.cosmic.common.tile.TileIndustrialSawmill;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static BlockBase machineCasing = new BlockMachineCasing();
    public static BlockBase machineFrame = new BlockMachineFrame();
    public static BlockBase industrialBlastFurnace = new BlockIndustrialBlastFurnace();
    public static BlockBase industrialGrinder = new BlockIndustrialGrinder();
    public static BlockBase industrialSawmill = new BlockIndustrialSawmill();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        r.register(machineCasing);
        r.register(machineFrame);
        r.register(industrialBlastFurnace);
        r.register(industrialGrinder);
        r.register(industrialSawmill);

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileIndustrialBlastFurnace.class, Lib.MOD_ID + ":" + Lib.Blocks.INDUSTRIAL_BLAST_FURNACE);
        GameRegistry.registerTileEntity(TileIndustrialGrinder.class, Lib.MOD_ID + ":" + Lib.Blocks.INDUSTRIAL_GRINDER);
        GameRegistry.registerTileEntity(TileIndustrialSawmill.class, Lib.MOD_ID + ":" + Lib.Blocks.INDUSTRIAL_SAWMILL);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(machineCasing.createItemBlock());
        r.register(machineFrame.createItemBlock());
        r.register(industrialBlastFurnace.createItemBlock());
        r.register(industrialGrinder.createItemBlock());
        r.register(industrialSawmill.createItemBlock());
    }
}
