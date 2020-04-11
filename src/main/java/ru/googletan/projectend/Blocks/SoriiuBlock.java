package ru.googletan.projectend.Blocks;


import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class SoriiuBlock extends BlockAir
{
    public SoriiuBlock(String name)
    {

        setRegistryName(name);
    }
    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){return true;}

    @Override
    public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {return false;}
    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    @Override
   public EnumBlockRenderType getRenderType(IBlockState state)
   {    return EnumBlockRenderType.INVISIBLE;
    }
}
