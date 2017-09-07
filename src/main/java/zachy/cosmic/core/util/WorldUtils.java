package zachy.cosmic.core.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldUtils {

    /**
     *  Safely retrieves the tile entity. Should only ever be called from
     *  Block#getActualState and Block#getExtendedState
     *
     *  @param world the world the tile entity is in
     *  @param pos the tile entity's position
     *
     *  @return the tile entity
     */
    public static TileEntity getTileSafely(IBlockAccess world, BlockPos pos) {
        if (world instanceof ChunkCache) {
            return ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
        }

        return world.getTileEntity(pos);
    }

    /**
     * Updates the given block
     *
     * @param world the world the block is in
     * @param pos the block's position
     */
    public static void updateBlock(World world, BlockPos pos) {
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 1 | 2);
        }
    }
}
