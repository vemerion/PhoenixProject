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
import phoenix.title.death.TileTank;

import javax.annotation.Nullable;

public class BlockTank extends BlockTileEntity
{
    public BlockTank()
    {
        super("tank_dead", Material.GLASS, 5, 5, SoundType.GLASS);
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }

    @Override
    public Class getTileEntityClass()
    {
        return TileTank.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState)
    {
        return new TileTank();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileTank tileTank = (TileTank) worldIn.getTileEntity(pos);
        if (playerIn.getHeldItem(hand).getItem() == Items.BUCKET && FluidUtil.getFluidContained(playerIn.getHeldItem(hand)) == null)
        {
            FluidStack stack = tileTank.getTank().drain(1000, true);
            playerIn.setHeldItem(hand, stack != null ? FluidUtil.getFilledBucket(stack) : new ItemStack(Items.BUCKET));
            return false;
        }

        if(FluidUtil.getFluidContained(playerIn.getHeldItem(hand)) != null)
        {
            tileTank.getTank().fill(FluidUtil.getFluidContained(playerIn.getHeldItem(hand)), true);
            playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
            return false;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    @Override public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    @Override public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
