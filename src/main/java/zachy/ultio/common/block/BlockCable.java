package zachy.ultio.common.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zachy.ultio.client.core.handler.ModelHandler;
import zachy.ultio.common.core.Lib;
import zachy.ultio.common.core.util.WorldUtils;
import zachy.ultio.common.tile.TileBase;
import zachy.ultio.common.tile.TileCable;

import java.util.ArrayList;
import java.util.List;

public class BlockCable extends BlockBase {

    private static final IProperty<CableType> TYPE = PropertyEnum.create("type", CableType.class);

    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");

    private static final AxisAlignedBB CORE_AABB = WorldUtils.getAABB(6, 6, 6, 10, 10, 10);
    private static final AxisAlignedBB NORTH_AABB = WorldUtils.getAABB(6, 6, 0, 10, 10, 6);
    private static final AxisAlignedBB EAST_AABB = WorldUtils.getAABB(10, 6, 6, 16, 10, 10);
    private static final AxisAlignedBB SOUTH_AABB = WorldUtils.getAABB(6, 6, 10, 10, 10, 16);
    private static final AxisAlignedBB WEST_AABB = WorldUtils.getAABB(0, 6, 6, 6, 10, 10);
    private static final AxisAlignedBB UP_AABB = WorldUtils.getAABB(6, 10, 6, 10, 16, 10);
    private static final AxisAlignedBB DOWN_AABB = WorldUtils.getAABB(6, 0, 6, 10, 6, 10);

    public BlockCable() {
        super(Lib.Blocks.CABLE);

        setDefaultState(getDefaultState().withProperty(TYPE, CableType.COPPER).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCable();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, CableType.values()[meta]);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos) {
        TileEntity tile = WorldUtils.getTile(access, pos);

        state = super.getActualState(state, access, pos)
                .withProperty(TYPE, state.getValue(TYPE))
                .withProperty(NORTH, isConnectedTo(access, pos, tile, EnumFacing.NORTH))
                .withProperty(EAST, isConnectedTo(access, pos, tile, EnumFacing.EAST))
                .withProperty(SOUTH, isConnectedTo(access, pos, tile, EnumFacing.SOUTH))
                .withProperty(WEST, isConnectedTo(access, pos, tile, EnumFacing.WEST))
                .withProperty(UP, isConnectedTo(access, pos, tile, EnumFacing.UP))
                .withProperty(DOWN, isConnectedTo(access, pos, tile, EnumFacing.DOWN));

        return state;
    }

    private boolean isConnectedTo(IBlockAccess access, BlockPos pos, TileEntity tile, EnumFacing direction) {
        if (!(tile instanceof TileCable)) {
            return false;
        }

        TileEntity adjacentTile = access.getTileEntity(pos.offset(direction));

        if (adjacentTile != null) {
            if (adjacentTile instanceof TileBase) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (CableType type : CableType.values()) {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getMetaFromState(state));
    }

    private List<AxisAlignedBB> getCollisionBoxes(IBlockState state) {
        List<AxisAlignedBB> boxes = new ArrayList<>();

        boxes.add(CORE_AABB);

        if (state.getValue(NORTH)) {
            boxes.add(NORTH_AABB);
        }

        if (state.getValue(EAST)) {
            boxes.add(EAST_AABB);
        }

        if (state.getValue(SOUTH)) {
            boxes.add(SOUTH_AABB);
        }

        if (state.getValue(WEST)) {
            boxes.add(WEST_AABB);
        }

        if (state.getValue(UP)) {
            boxes.add(UP_AABB);
        }

        if (state.getValue(DOWN)) {
            boxes.add(DOWN_AABB);
        }

        return boxes;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        for (AxisAlignedBB aabb : getCollisionBoxes(getActualState(state, world, pos))) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
        }
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        WorldUtils.AdvancedRayTraceResult result = WorldUtils.collisionRayTrace(pos, start, end, getCollisionBoxes(this.getActualState(state, world, pos)));

        return result != null ? result.hit : null;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        state = state.getActualState(access, pos);

        float minX = state.getValue(WEST) ? 0.0F : 0.375F;
        float minY = state.getValue(DOWN) ? 0.0F : 0.375F;
        float minZ = state.getValue(NORTH) ? 0.0F : 0.375F;
        float maxX = state.getValue(EAST) ? 1.0F : 0.625F;
        float maxY = state.getValue(UP) ? 1.0F : 0.625F;
        float maxZ = state.getValue(SOUTH) ? 1.0F : 0.625F;

        return new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void registerModel() {
        ModelHandler.registerItemBlock(this, 1, i -> "cable_" + CableType.values()[i]);
    }
}
