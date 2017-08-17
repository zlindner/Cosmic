package zachy.cosmic.common.block.base;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zachy.cosmic.common.core.init.ModBlocks;
import zachy.cosmic.common.core.util.StackUtils;
import zachy.cosmic.common.core.util.WorldUtils;
import zachy.cosmic.common.item.ItemBlockBase;
import zachy.cosmic.common.tile.base.TileMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockMachine extends BlockBase implements IWrenchable {

    protected static final PropertyDirection DIRECTION = PropertyDirection.create("direction", Arrays.asList(EnumFacing.HORIZONTALS));

    public BlockMachine(String name) {
        super(name);

        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 0);

        setDefaultState(getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH));
    }

    @Override
    public Item createItemBlock() {
        ItemBlockBase itemBlock = new ItemBlockBase(this);

        itemBlock.setHorizontal();

        return itemBlock;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = WorldUtils.getTile(world, pos);

        StackUtils.dropInventory(world, pos, (IInventory) tile);

        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (isBasic()) {
            drops.add(new ItemStack(ModBlocks.machine_frame, 1, 0));
        }

        if (isIntermediate()) {
            drops.add(new ItemStack(ModBlocks.machine_frame, 1, 1));
        }

        if (isAdvanced()) {
            drops.add(new ItemStack(ModBlocks.machine_frame, 1, 2));
        }
    }

    public boolean isBasic() {
        return false;
    }

    public boolean isIntermediate() {
        return false;
    }

    public boolean isAdvanced() {
        return false;
    }

    /*
     * IWrenchable
     */
    @Override
    public EnumFacing getFacing(World world, BlockPos pos) {
        TileMachine tile = (TileMachine) WorldUtils.getTile(world, pos);

        return tile.getDirection();
    }

    @Override
    public boolean setFacing(World world, BlockPos pos, EnumFacing facing, EntityPlayer player) {
        if (!player.isSneaking()) {
            TileMachine tile = (TileMachine) WorldUtils.getTile(world, pos);

            tile.setDirection(tile.getDirection().rotateY());

            return true;
        }

        return false;
    }

    @Override
    public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
        return player.isSneaking();
    }

    @Override
    public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity tile, EntityPlayer player, int i) {
        List<ItemStack> drop = new ArrayList<>();

        drop.add(new ItemStack(this));

        return drop;
    }
}
