package zachy.cosmic.block.base;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zachy.cosmic.core.handler.ModelHandler;
import zachy.cosmic.core.util.StackUtils;
import zachy.cosmic.core.util.WorldUtils;
import zachy.cosmic.item.base.ItemBlockBase;
import zachy.cosmic.tile.base.TileMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockMachine extends BlockBase implements IWrenchable {

    protected static final PropertyBool ACTIVE = PropertyBool.create("active");
    protected static final PropertyDirection DIRECTION = PropertyDirection.create("direction", Arrays.asList(EnumFacing.HORIZONTALS));

    public BlockMachine(String name) {
        super(name);

        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public Item createItemBlock() {
        ItemBlockBase itemBlock = new ItemBlockBase(this);

        itemBlock.setDirectional();

        return itemBlock;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState getBaseState() {
        return getDefaultState().withProperty(ACTIVE, false).withProperty(DIRECTION, EnumFacing.NORTH);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ACTIVE, DIRECTION);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = WorldUtils.getTileSafely(world, pos);

        if (tile instanceof TileMachine) {
            return getDefaultState().withProperty(ACTIVE, ((TileMachine) tile).isActive()).withProperty(DIRECTION, ((TileMachine) tile).getDirection());
        }

        return state;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);

        StackUtils.dropInventory(world, pos, (IInventory) tile);

        super.breakBlock(world, pos, state);
    }

    @Override
    public void registerModel() {
        ModelHandler.registerBlock(this, 0, getDefaultState().withProperty(ACTIVE, false).withProperty(DIRECTION, EnumFacing.NORTH));
    }

    /*
     * IWrenchable
     */
    @Override
    public EnumFacing getFacing(World world, BlockPos pos) {
        TileMachine tile = (TileMachine) world.getTileEntity(pos);

        return tile.getDirection();
    }

    @Override
    public boolean setFacing(World world, BlockPos pos, EnumFacing facing, EntityPlayer player) {
        if (!player.isSneaking()) {
            TileMachine tile = (TileMachine) world.getTileEntity(pos);

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

    @Override
    public boolean isBasic() {
        return true;
    }
}
