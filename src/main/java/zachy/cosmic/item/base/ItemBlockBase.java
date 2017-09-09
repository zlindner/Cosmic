package zachy.cosmic.item.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zachy.cosmic.tile.base.TileBase;

public class ItemBlockBase extends ItemBlock {

    private boolean directional;

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
            return getUnlocalizedName() + "_" + stack.getItemDamage();
        }

        return getUnlocalizedName();
    }

    public void setHasMeta() {
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public void setDirectional() {
        directional = true;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState state) {
        boolean result = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, state);

        if (result && directional) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileBase) {
                ((TileBase) tile).setDirection(player.getHorizontalFacing().getOpposite());
            }
        }

        return result;
    }
}
