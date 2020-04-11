package ru.googletan.projectend.Blocks.Unit01;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class Basor01 extends Block
{
    public Basor01(String name)
    {
        super(Material.IRON);
        this.setRegistryName(name);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}