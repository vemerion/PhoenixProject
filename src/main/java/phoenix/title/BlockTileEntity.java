package phoenix.title;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTileEntity<T extends TileEntity> extends Block
{
	public BlockTileEntity(String name, Material material, float hardness, float resistanse, SoundType soundType)
	{
		super(material);
		setRegistryName(name);
		setTranslationKey(name);
		setHardness(hardness);
		setResistance(resistanse);
		setSoundType(soundType);
		setTranslationKey(name);
	}

	public BlockTileEntity(String name)
	{
		super(Material.ROCK);
		setRegistryName(name);
		setTranslationKey(name);
	}

	public abstract Class<T> getTileEntityClass();

	public T getTileEntity(IBlockAccess world, BlockPos position)
	{
		return (T) world.getTileEntity(position);
	}

	@Override public boolean hasTileEntity(IBlockState blockState)
	{
		return true;
	}
	@Nullable @Override	public abstract T createTileEntity(World world, IBlockState blockState);
}
