package zachy.cosmic.item;

import ic2.api.item.IElectricItem;
import ic2.core.item.ElectricItemManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import zachy.cosmic.block.base.BlockMachine;
import zachy.cosmic.core.Lib;
import zachy.cosmic.item.base.ItemBase;
import zachy.cosmic.tile.base.TileMachine;
import zachy.cosmic.tile.base.TileMultiblockController;

public class ItemPortableScanner extends ItemBase implements IElectricItem {

    public ItemPortableScanner() {
        super(Lib.Items.PORTABLE_SCANNER);

        setMaxStackSize(1);
        setMaxDamage(27);
    }

    //TODO add fancy colours to text
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            String blockName = block.getUnlocalizedName();
            int metadata = block.getMetaFromState(state);
            float hardness = state.getBlockHardness(world, pos);
            float resistance = block.getExplosionResistance(player); //TODO fix

            player.sendMessage(new TextComponentString("Name: " + blockName + " - Metadata: " + metadata));
            player.sendMessage(new TextComponentString("Hardness: " + hardness + " - Blast Resistance: " + resistance));

            if (block instanceof BlockMachine) {
                TileEntity tile = world.getTileEntity(pos);

                if (tile instanceof TileMachine) {
                    TileMachine machine = (TileMachine) tile;

                    boolean enet = machine.addedToEnet();
                    double maxInput = machine.getMaxInput();
                    double maxOutput = machine.getMaxOutput();
                    double maxStored = machine.getMaxStored();
                    double currentEnergy = machine.getEnergy();
                    String direction = machine.getDirection().getName();
                    direction = Character.toUpperCase(direction.charAt(0)) + direction.substring(1);

                    player.sendMessage(new TextComponentString("Added to Enet: " + enet));
                    player.sendMessage(new TextComponentString("Max Input: " + maxInput + " - Max Output: " + maxOutput));
                    player.sendMessage(new TextComponentString("Max Stored: " + maxStored + " - Current Energy: " + currentEnergy));
                    player.sendMessage(new TextComponentString("Direction: " + direction));

                    if (tile instanceof TileMultiblockController) {
                        TileMultiblockController controller = (TileMultiblockController) tile;

                        boolean valid = controller.isValid();

                        player.sendMessage(new TextComponentString("Valid Structure: " + valid));
                    }
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> stacks) {
        if (isInCreativeTab(tab)) {
            ElectricItemManager.addChargeVariants(this, stacks);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.getMetadata() != 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return 100000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 100;
    }
}
