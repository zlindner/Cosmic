package zachy.cosmic.core.util;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.core.init.ModBlocks;

public class MultiblockUtils {

    public static boolean isAnyCasing(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.machine_casing;
    }

    public static boolean isBasicCasing(World world, BlockPos pos) {
        return world.getBlockState(pos) == ModBlocks.machine_casing.getStateFromMeta(0);
    }

    public static boolean isIntermediateCasing(World world, BlockPos pos) {
        return world.getBlockState(pos) == ModBlocks.machine_casing.getStateFromMeta(1);
    }

    public static boolean isAdvancedCasing(World world, BlockPos pos) {
        return world.getBlockState(pos) == ModBlocks.machine_casing.getStateFromMeta(2);
    }

    public static boolean isLava(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.LAVA;
    }

    public static boolean isAir(World world, BlockPos pos) {
        return world.isAirBlock(pos);
    }

    public static boolean isWater(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.WATER;
    }
}
