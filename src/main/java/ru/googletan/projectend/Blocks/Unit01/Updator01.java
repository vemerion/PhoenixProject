package ru.googletan.projectend.Blocks.Unit01;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.googletan.projectend.Blocks.title.BlockTileEntity;
import ru.googletan.projectend.Blocks.title.Unit01.Updator01TitleEntity;
import ru.googletan.projectend.Projectend;

import javax.annotation.Nullable;

public class Updator01 extends BlockTileEntity
{
    public Updator01(String name, Material material, float hardness, float resistanse, SoundType soundType)
    {
        super(name, material, hardness, resistanse, soundType);
        this.setCreativeTab(Projectend.TheEndOfCreativeTabs);
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
    @Override
    public Class getTileEntityClass()
    {
        return Updator01TitleEntity.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState)
    {
        return new Updator01TitleEntity();
    }
}
