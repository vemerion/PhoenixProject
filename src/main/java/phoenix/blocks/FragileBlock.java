package phoenix.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import phoenix.title.BlockTileEntity;
import phoenix.title.TileFragileBlock;

import javax.annotation.Nullable;
import java.util.Random;

public class FragileBlock extends BlockTileEntity
{
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0 , 15);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.1D, 0.1D, 0.1D, 0.9D, 0.4D, 0.9D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.1D, 0.4D, 0.1D, 0.9D, 0.9D, 0.9D);

    public FragileBlock()
    {
      super("fragileblock", Material.ROCK, 3.0F,15.0F, SoundType.GROUND);
      setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            if (entityIn instanceof EntityPlayer)
            {
                for (int x = pos.getX() - 20; x < pos.getX() + 20; x++)
                {
                    for (int z = pos.getZ() - 20; z < pos.getZ() + 20; z++)
                    {
                        for (int y = pos.getY() - 20; y < pos.getY() + 20; y++)
                        {
                            if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof FragileBlock)
                            {
                                if (((TileFragileBlock) getTileEntity(worldIn, new BlockPos(x, y, z))).getTimer() == -1)
                                {
                                    ((TileFragileBlock) getTileEntity(worldIn, new BlockPos(x, y, z))).setTimer(10 + new Random().nextInt(25));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos position, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote)
        {
            TileFragileBlock tileEntity = (TileFragileBlock) getTileEntity(world, position);
            if (side == EnumFacing.DOWN) {
                tileEntity.decrementCount();
            }

            else if (side == EnumFacing.UP) {
                tileEntity.incrementCount();
            }
            player.sendMessage(new TextComponentString("Count: " + tileEntity.getCount()));
        }
        return true;
    }
    @Override  public Class getTileEntityClass() {  return TileFragileBlock.class;  }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(state.equals(this.blockState.getBaseState().withProperty(TYPE, 0)))
            return Item.getItemFromBlock(Blocks.END_STONE);
        if(state.equals(this.blockState.getBaseState().withProperty(TYPE, 1)))
            return Item.getItemFromBlock(Blocks.END_BRICKS);
        if(state.equals(this.blockState.getBaseState().withProperty(TYPE, 2)))
            return Item.getItemFromBlock(Blocks.PURPUR_BLOCK);

        return null;
    }

    @Override  protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {TYPE});  }
    @Override  public IBlockState getStateFromMeta(int meta)    { return this.getDefaultState().withProperty(TYPE, meta);  }
    @Override  public int getMetaFromState(IBlockState state)   { return state.getValue(TYPE);  }
    @Override  public boolean isFullCube(IBlockState state)     { return false; }
    @Override  public boolean isOpaqueCube(IBlockState state)   { return false; }
    @Override  public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) { return false; }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(state.getValue(TYPE) == 2)
            return AABB_TOP_HALF;
        if(state.getValue(TYPE) == 3)
            return AABB_BOTTOM_HALF;

        return FULL_BLOCK_AABB;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) { return new TileFragileBlock(); }
}
