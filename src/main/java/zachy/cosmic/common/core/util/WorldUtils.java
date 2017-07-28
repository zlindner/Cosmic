package zachy.cosmic.common.core.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldUtils {

    public static TileEntity getTile(IBlockAccess world, BlockPos pos) {
        if (world instanceof ChunkCache) {
            return ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
        }

        return world.getTileEntity(pos);
    }

    public static void updateBlock(World world, BlockPos pos) {
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 1 | 2);
        }
    }
}
