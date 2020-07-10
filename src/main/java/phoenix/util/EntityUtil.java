package phoenix.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.function.DoubleUnaryOperator;

public class EntityUtil
{
    public static boolean canDestroyBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        float hardness = state.getBlockHardness(world, pos);
        return hardness >= 0f && hardness < 50f && !state.getBlock().isAir(state, world, pos)
                && state.getBlock().canEntityDestroy(state, world, pos, entity)
                && (/* rude type limit */!(entity instanceof EntityLivingBase)
                || ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase) entity, pos, state));
    }
}
