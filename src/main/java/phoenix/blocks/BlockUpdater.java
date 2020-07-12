package phoenix.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import phoenix.Phoenix;
import phoenix.title. BlockTileEntity;
import phoenix.title.TileUpdater;

import javax.annotation.Nullable;
public class BlockUpdater extends BlockTileEntity
{
    public BlockUpdater()
    {
        super("updater", Material.BARRIER, 10, 10, SoundType.GLASS);
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        setTranslationKey("updater");
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
        return TileUpdater.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState)
    {
        return new TileUpdater();
    }
}
