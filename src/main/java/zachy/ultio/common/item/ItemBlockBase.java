package zachy.ultio.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.ultio.common.block.Direction;
import zachy.ultio.common.tile.TileBase;

public class ItemBlockBase extends ItemBlock {

    private Direction direction;

    public ItemBlockBase(Block block, Direction direction, boolean subtypes) {
        super(block);

        this.direction = direction;

        setRegistryName(block.getRegistryName());

        if (subtypes) {
            setMaxDamage(0);
            setHasSubtypes(true);
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getHasSubtypes()) {
            return getUnlocalizedName() + "." + stack.getItemDamage();
        }

        return getUnlocalizedName();
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState state) {
        boolean result = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, state);

        if (result && direction != null) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileBase) {
                ((TileBase) tile).setDirection(direction.getFrom(side, pos, player));
            }
        }

        return result;
    }
}
