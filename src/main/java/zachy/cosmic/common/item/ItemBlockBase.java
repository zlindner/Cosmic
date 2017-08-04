package zachy.cosmic.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.common.tile.base.TileBase;

public class ItemBlockBase extends ItemBlock {

    private boolean horizontal = false;
    private boolean multidirectional = false;

    public ItemBlockBase(Block block) {
        super(block);

        setRegistryName(block.getRegistryName());
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

    public void setHasMeta() {
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public void setHorizontal() {
        horizontal = true;
    }

    public void setMultidirectional() {
        multidirectional = true;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState state) {
        boolean result = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, state);

        if (result && (horizontal || multidirectional)) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileBase) {
                if (horizontal) {
                    ((TileBase) tile).setDirection(player.getHorizontalFacing().getOpposite());
                } else if (multidirectional) {
                    ((TileBase) tile).setDirection(EnumFacing.getDirectionFromEntityLiving(pos, player));
                }
            }
        }

        return result;
    }
}
