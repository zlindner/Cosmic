package zachy.cosmic.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import zachy.cosmic.block.*;
import zachy.cosmic.block.base.BlockBase;
import zachy.cosmic.block.machine.BlockCentrifuge;
import zachy.cosmic.block.machine.BlockChemicalReactor;
import zachy.cosmic.block.machine.BlockElectrolyzer;
import zachy.cosmic.block.multiblock.*;
import zachy.cosmic.core.Lib;
import zachy.cosmic.tile.machine.TileCentrifuge;
import zachy.cosmic.tile.machine.TileChemicalReactor;
import zachy.cosmic.tile.machine.TileElectrolyzer;
import zachy.cosmic.tile.multiblock.*;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static BlockBase blast_furnace = new BlockBlastFurnace();
    public static BlockBase centrifuge = new BlockCentrifuge();
    public static BlockBase chemical_reactor = new BlockChemicalReactor();
    public static BlockBase compressor = new BlockCompressor();
    public static BlockBase distillation_tower = new BlockDistillationTower();
    public static BlockBase electrolyzer = new BlockElectrolyzer();
    public static BlockBase grinder = new BlockGrinder();
    public static BlockBase machine_casing = new BlockMachineCasing();
    public static BlockBase machine_frame = new BlockMachineFrame();
    public static BlockBase sawmill = new BlockSawmill();
    public static BlockBase vacuum_freezer = new BlockVacuumFreezer();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        r.register(blast_furnace);
        r.register(centrifuge);
        r.register(chemical_reactor);
        r.register(compressor);
        r.register(distillation_tower);
        r.register(electrolyzer);
        r.register(grinder);
        r.register(machine_casing);
        r.register(machine_frame);
        r.register(sawmill);
        r.register(vacuum_freezer);

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TileBlastFurnace.class, Lib.MOD_ID + ":" + Lib.Blocks.BLAST_FURNACE);
        GameRegistry.registerTileEntity(TileCentrifuge.class, Lib.MOD_ID + ":" + Lib.Blocks.CENTRIFUGE);
        GameRegistry.registerTileEntity(TileChemicalReactor.class, Lib.MOD_ID + ":" + Lib.Blocks.CHEMICAL_REACTOR);
        GameRegistry.registerTileEntity(TileCompressor.class, Lib.MOD_ID + ":" + Lib.Blocks.COMPRESSOR);
        GameRegistry.registerTileEntity(TileDistillationTower.class, Lib.MOD_ID + ":" + Lib.Blocks.DISTILLATION_TOWER);
        GameRegistry.registerTileEntity(TileElectrolyzer.class, Lib.MOD_ID + ":" + Lib.Blocks.ELECTROLYZER);
        GameRegistry.registerTileEntity(TileGrinder.class, Lib.MOD_ID + ":" + Lib.Blocks.GRINDER);
        GameRegistry.registerTileEntity(TileSawmill.class, Lib.MOD_ID + ":" + Lib.Blocks.SAWMILL);
        GameRegistry.registerTileEntity(TileVacuumFreezer.class, Lib.MOD_ID + ":" + Lib.Blocks.VACUUM_FREEZER);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        r.register(blast_furnace.createItemBlock());
        r.register(centrifuge.createItemBlock());
        r.register(chemical_reactor.createItemBlock());
        r.register(compressor.createItemBlock());
        r.register(distillation_tower.createItemBlock());
        r.register(electrolyzer.createItemBlock());
        r.register(grinder.createItemBlock());
        r.register(machine_casing.createItemBlock());
        r.register(machine_frame.createItemBlock());
        r.register(sawmill.createItemBlock());
        r.register(vacuum_freezer.createItemBlock());
    }
}
