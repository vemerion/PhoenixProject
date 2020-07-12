package phoenix.blocks.redo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockChorusPlant;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.init.BlocksRegister;
import phoenix.init.Common;

import java.util.ArrayList;

public class BlockKikinStem extends BlockChorusPlant
{

    public BlockKikinStem()
    {
        super();
        setRegistryName("kikin_stem");
        setTranslationKey("kikin_stem");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);

        Common.addBlock(this);
        Common.addItem(Item.getItemFromBlock(this));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        Block block2 = worldIn.getBlockState(pos.north()).getBlock();
        Block block3 = worldIn.getBlockState(pos.east()).getBlock();
        Block block4 = worldIn.getBlockState(pos.south()).getBlock();
        Block block5 = worldIn.getBlockState(pos.west()).getBlock();
        return state
            .withProperty(DOWN, Boolean.valueOf(block  == this || block == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE))
            .withProperty(UP,   Boolean.valueOf(block1 == this || block1 == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE))
            .withProperty(NORTH,Boolean.valueOf(block2 == this || block2 == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE))
            .withProperty(EAST, Boolean.valueOf(block3 == this || block3 == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE))
            .withProperty(SOUTH,Boolean.valueOf(block4 == this || block4 == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE))
            .withProperty(WEST, Boolean.valueOf(block5 == this || block5 == BlocksRegister.KIKIN_FRUIT || block == BlocksRegister.FERTILE_END_STONE));

    }

    @Override
    public boolean canSurviveAt(World wordIn, BlockPos pos)
    {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = wordIn.getBlockState(blockpos).getBlock();

            if (block == this)
            {
                if (!flag && !flag1)
                {
                    return false;
                }

                Block block1 = wordIn.getBlockState(blockpos.up()).getBlock();

                if (block1 == this || block1 == BlocksRegister.FERTILE_END_STONE)
                {
                    return true;
                }
            }
        }

        Block block2 = wordIn.getBlockState(pos.up()).getBlock();
        return block2 == this || block2 == BlocksRegister.FERTILE_END_STONE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && block != BlocksRegister.KIKIN_FRUIT && (side != EnumFacing.UP || block != BlocksRegister.FERTILE_END_STONE);
    }


}
