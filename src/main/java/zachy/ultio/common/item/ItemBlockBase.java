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

    private int directional;

    public ItemBlockBase(Block block) {
        this(block, false, -1);
    }

    public ItemBlockBase(Block block, boolean subtypes) {
        this(block, subtypes, -1);
    }

    public ItemBlockBase(Block block, boolean subtypes, int directional) {
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

        if (result && directional != -1) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileBase) {
                if (directional == 0) { // probably a better way of handling this
                    ((TileBase) tile).setDirection(player.getHorizontalFacing().getOpposite());
                } else if (directional == 1) {
                    ((TileBase) tile).setDirection(EnumFacing.getDirectionFromEntityLiving(pos, player));
                }
            }
        }

        return result;
    }
}
