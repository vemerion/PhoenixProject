package phoenix.blocks.redo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockChorusPlant;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.ForgeHooks;
import phoenix.Phoenix;
import phoenix.init.BlocksRegister;
import phoenix.init.Common;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class BlockKikiNFruit extends BlockChorusFlower
{
    public BlockKikiNFruit()
    {
        super();
        setRegistryName("kikin_fruit");
        setTranslationKey("kikin_fruit");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        setTickRandomly(true);

        Common.addBlock(this);
        Common.addItem(Item.getItemFromBlock(this));
    }


    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!this.canSurvive(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
        else
        {
            BlockPos blockpos = pos.down();

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() > 0)
            {
                int i = state.getValue(AGE);

                if (i < 5 && ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, rand.nextInt(1) == 0))
                {
                    boolean flag = false;
                    boolean hasEndStone = false;
                    IBlockState iblockstate = worldIn.getBlockState(pos.up());
                    Block block = iblockstate.getBlock();

                    if (block == BlocksRegister.FERTILE_END_STONE)     flag = true;
                    else if (block == BlocksRegister.KIKIN_PLANT)
                    {
                        int j = 1;

                        for (int k = 0; k < 4; ++k)
                        {
                            Block block1 = worldIn.getBlockState(pos.up(j + 1)).getBlock();
                            if (block1 != BlocksRegister.KIKIN_PLANT)
                            {
                                if (block1 == BlocksRegister.FERTILE_END_STONE)  hasEndStone = true;
                                break;
                            }
                            ++j;
                        }
                        int i1 = 4;
                        if (hasEndStone)  ++i1;

                        if (j < 2 || rand.nextInt(i1) >= j) flag = true;
                    }
                    else if (iblockstate.getMaterial() == Material.AIR)
                    {
                        flag = true;
                    }

                    if (flag && areAllNeighborsEmpty(worldIn, blockpos, null) && worldIn.isAirBlock(pos.down(2)))
                    {
                        worldIn.setBlockState(pos, BlocksRegister.KIKIN_PLANT.getDefaultState(), 2);
                        this.placeGrownFlower(worldIn, blockpos, i);
                    }
                    else if (i < 4)
                    {
                        int l = rand.nextInt(4);
                        boolean flag2 = false;

                        if (hasEndStone) ++l;

                        for (int j1 = 0; j1 < l; ++j1)
                        {
                            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos blockpos1 = pos.offset(enumfacing);

                            if (worldIn.isAirBlock(blockpos1) &&
                                    worldIn.isAirBlock(blockpos1.down()) &&
                                    areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                            {
                                this.placeGrownFlower(worldIn, blockpos1, i + 1);
                                flag2 = true;
                            }
                        }

                        if (flag2)
                        {
                            worldIn.setBlockState(pos, BlocksRegister.KIKIN_PLANT.getDefaultState(), 2);
                        }
                        else
                        {
                            this.placeDeadFlower(worldIn, pos);
                        }
                    }
                    else if (i == 4)
                    {
                        this.placeDeadFlower(worldIn, pos);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    private void placeGrownFlower(World worldIn, BlockPos pos, int age)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(age)), 2);
        worldIn.playEvent(1033, pos, 0);
    }

    private void placeDeadFlower(World worldIn, BlockPos pos)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        worldIn.playEvent(1034, pos, 0);
    }

    private static boolean areAllNeighborsEmpty(World worldIn, BlockPos pos, EnumFacing excludingSide)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing)))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canSurvive(World worldIn, BlockPos pos)
    {
        IBlockState upperblockstate = worldIn.getBlockState(pos.up());
        Block upblock = upperblockstate.getBlock();

        if (upblock != BlocksRegister.KIKIN_PLANT && upblock != BlocksRegister.FERTILE_END_STONE)
        {
            if (upperblockstate.getMaterial() == Material.AIR)
            {
                int i = 0;

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    IBlockState currect = worldIn.getBlockState(pos.offset(enumfacing));
                    Block block1 = currect.getBlock();

                    if (block1 == BlocksRegister.KIKIN_PLANT)
                    {
                        ++i;
                    }
                    else if (currect.getMaterial() != Material.AIR)
                    {
                        return false;
                    }
                }

                return i == 1;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public static void generatePlant(World worldIn, BlockPos pos, Random rand, int p_185603_3_)
    {
        worldIn.setBlockState(pos, BlocksRegister.KIKIN_FRUIT.getDefaultState(), 2);
        growTreeRecursive(worldIn, pos, rand, pos, p_185603_3_, 0);
    }

    private static void growTreeRecursive(World worldIn, BlockPos pos, Random rand, BlockPos pos2, int p_185601_4_, int p_185601_5_)
    {
        int i = rand.nextInt(4) + 1;

        if (p_185601_5_ == 0)
        {
            ++i;
        }

        for (int j = 0; j < i; ++j)
        {
            BlockPos blockpos = pos.down(j + 1);

            if (!areAllNeighborsEmpty(worldIn, blockpos, null))
            {
                return;
            }

            worldIn.setBlockState(blockpos, BlocksRegister.KIKIN_PLANT.getDefaultState(), 2);
        }

        boolean flag = false;

        if (p_185601_5_ < 4)
        {
            int l = rand.nextInt(4);

            if (p_185601_5_ == 0)
            {
                ++l;
            }

            for (int k = 0; k < l; ++k)
            {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = pos.down(i).offset(enumfacing);

                if (Math.abs(blockpos1.getX() - pos2.getX()) < p_185601_4_ &&
                        Math.abs(blockpos1.getZ() - pos2.getZ()) < p_185601_4_ &&
                        worldIn.isAirBlock(blockpos1) &&
                        worldIn.isAirBlock(blockpos1.up()) &&
                        areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite()))
                {
                    flag = true;
                    worldIn.setBlockState(blockpos1, Blocks.CHORUS_PLANT.getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, pos2, p_185601_4_, p_185601_5_ + 1);
                }
            }
        }

        if (!flag)
        {
            worldIn.setBlockState(pos.up(i), BlocksRegister.KIKIN_FRUIT.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        }
    }
}
