package zachy.cosmic.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import zachy.cosmic.client.IModelRegister;
import zachy.cosmic.core.Lib;
import zachy.cosmic.core.CosmicTab;
import zachy.cosmic.core.init.ModBlocks;
import zachy.cosmic.item.base.ItemBlockBase;

public class BlockBase extends Block implements IModelRegister {

    public BlockBase(String name) {
        super(Material.ROCK);

        setHardness(2.0F);
        //setResistance()

        setDefaultState(getBaseState());

        setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(Lib.MOD_ID, name));
        setCreativeTab(CosmicTab.INSTANCE);
    }

    public Item createItemBlock() {
        return new ItemBlockBase(this);
    }

    public IBlockState getBaseState() {
        return getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
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

    @Override
    public void registerModel() {
        // NO-OP
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
}
