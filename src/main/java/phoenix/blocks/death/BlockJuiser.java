package phoenix.blocks.death;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.title.BlockTileEntity;
import phoenix.title.death.TileJuiser;
import phoenix.title.death.TileTank;

import javax.annotation.Nullable;

public class BlockJuiser extends BlockTileEntity
{
    public BlockJuiser()
    {
        super("juiser", Material.GLASS, 5, 5, SoundType.GLASS);
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }

    @Override
    public Class getTileEntityClass()
    {
        return TileJuiser.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState)
    {
        return new TileJuiser();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockTank && worldIn.getBlockState(pos.down()).getBlock() instanceof BlockTank) {
            TileTank up = (TileTank) worldIn.getTileEntity(pos.up());
            TileTank down = (TileTank) worldIn.getTileEntity(pos.down());
        }
        return  true;
    }

    @SideOnly(Side.CLIENT)
    @Override public BlockRenderLayer getRenderLayer(){return BlockRenderLayer.CUTOUT; }
    @Override public boolean isOpaqueCube(IBlockState state){  return false; }
    @Override public boolean isFullCube(IBlockState state)  { return false;  }
}