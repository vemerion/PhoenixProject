package phoenix.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;
import phoenix.init.BlocksRegister;

import javax.annotation.Nullable;

import static phoenix.blocks.BlockGost.TYPE;

public class GostTemplateProcessor implements ITemplateProcessor
{
    @Nullable
    @Override
    public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfoIn)
    {
        NBTTagCompound bnt = blockInfoIn.tileentityData;
        IBlockState state = blockInfoIn.blockState;
        if(worldIn.getBlockState(pos).getBlock() == Blocks.END_STONE)
        {
            state =  BlocksRegister.GOST.getDefaultState().withProperty(TYPE, 0);
        }
        else if(worldIn.getBlockState(pos).getBlock() == Blocks.END_BRICKS)
        {
            state =  BlocksRegister.GOST.getDefaultState().withProperty(TYPE, 1);
        }
        else if(worldIn.getBlockState(pos).getBlock() == Blocks.PURPUR_SLAB)
        {
            state =  BlocksRegister.GOST.getDefaultState().withProperty(TYPE, 2);
        }
        else if(worldIn.getBlockState(pos).getBlock() == Blocks.PURPUR_BLOCK)
        {
            state = BlocksRegister.GOST.getDefaultState().withProperty(TYPE, 4);
        }
        else if(Item.getItemFromBlock(worldIn.getBlockState(pos).getBlock()) instanceof ItemShulkerBox)
        {
            NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(blockInfoIn.tileentityData, items);
            items.set(worldIn.rand.nextInt(27), new ItemStack(Items.GOLDEN_APPLE, worldIn.rand.nextInt(5)));
            ItemStackHelper.saveAllItems(bnt, items);
        }
        return new Template.BlockInfo(pos, state, bnt);
    }  
}
