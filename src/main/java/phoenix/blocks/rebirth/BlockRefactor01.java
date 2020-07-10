package phoenix.blocks.rebirth;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import phoenix.title.BlockTileEntity;

import javax.annotation.Nullable;

public class BlockRefactor01 extends BlockTileEntity
{
    public BlockRefactor01(String name, Material material, float hardness, float resistanse, SoundType soundType)
    {
        super(name, material, hardness, resistanse, soundType);
    }

    @Override
    public Class getTileEntityClass()
    {
        return null;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState)
    {
        return null;
    }
}
