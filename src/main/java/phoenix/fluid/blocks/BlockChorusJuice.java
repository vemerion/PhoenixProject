package phoenix.fluid.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import phoenix.fluid.BlockFluidClassicFix;

import java.util.Random;

public class BlockChorusJuice extends BlockFluidClassicFix
{
    int collisionTimer;
    public BlockChorusJuice(Fluid fluid)
    {
        super(fluid);
        setRegistryName("chorus_juice");
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        collisionTimer++;
        float x = (float) entityIn.posX + new Random().nextFloat() * 3;
        float y = (float) entityIn.posY + new Random().nextFloat() * 3;
        float z = (float) entityIn.posZ + new Random().nextFloat() * 3;
        if(worldIn.isAirBlock(new BlockPos(new Vec3d(x, y, z))) && worldIn.isAirBlock(new BlockPos(new Vec3d(x, y - 1, z))) && !worldIn.getBlockState(new BlockPos(new Vec3d(x, y - 1, z))).equals(this));
        {
            entityIn.setPosition(x, y, z);
        }
    }
}
