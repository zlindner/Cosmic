package zachy.cosmic.common.core.util;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.common.core.init.ModBlocks;

public class MultiBlockUtils {

    public static boolean isCasing(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.machineCasing;
    }

    public static boolean isLava(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.LAVA;
    }

    public static boolean isAir(World world, BlockPos pos) {
        return world.isAirBlock(pos);
    }
}
