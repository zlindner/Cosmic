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
import zachy.ultio.common.tile.TileBase;

public class ItemBlockBase extends ItemBlock {

    private boolean directional;

    public ItemBlockBase(Block block) {
        this(block, false, false);
    }

    public ItemBlockBase(Block block, boolean subtypes) {
        this(block, subtypes, false);
    }

    public ItemBlockBase(Block block, boolean subtypes, boolean directional) {
        super(block);

        this.directional = directional;

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

        if (result && directional) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileBase) {
                ((TileBase) tile).setDirection(player.getHorizontalFacing().getOpposite()); // as of now only allows horizontal placements (i think?), in future may require verticals as well
                //((TileBase) tile).setDirection(EnumFacing.getDirectionFromEntityLiving(pos, player)); // implement check if block requires horizontal or any facing ^
            }
        }

        return result;
    }
}
