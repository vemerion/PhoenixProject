package phoenix.fluid;


import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;

public class BlockFluidClassicFix extends BlockFluidClassic
{

    public BlockFluidClassicFix(Fluid fluid) {
        super(fluid, Material.WATER);
    }

    @Override
    public float getFluidHeightForRender(IBlockAccess world, BlockPos pos, @Nonnull IBlockState up)
    {
        IBlockState here = world.getBlockState(pos);
        IBlockState up2 = world.getBlockState(pos.down(densityDir));
        if (here.getBlock() == this)
        {
            if (up2.getMaterial() == Material.WATER) return 1;
            if (getMetaFromState(here) == getMaxRenderHeightMeta()) return 8F/9;
        }
        if (here.getBlock() instanceof BlockLiquid)
        {
            if (here.getValue(BlockLiquid.LEVEL) == 8) return 1;
            else if (up2.getMaterial() == Material.WATER) return 1;
            return Math.min(1 - BlockLiquid.getLiquidHeightPercent(here.getValue(BlockLiquid.LEVEL)), 8F/9);
        }
        return !here.getMaterial().isSolid() && up2.getMaterial() == Material.WATER ? 1 : here.getBlock() == Blocks.AIR ? 0.0F : this.getQuantaPercentage(world, pos) * 8F/9;
    }


    @Override
    public float getFluidHeightAverage(float... flow) {
        float total = 0;
        int count = 0;
        float end = 0;
        for (int i = 0; i < flow.length; i++) {
            if (flow[i] == 1) return 1;
            if (flow[i] >= 8F/9) {
                total += flow[i] * 10;
                count += 10;
            }
            if (flow[i] >= 0) {
                total += flow[i];
                count++;
            }
        }
        if (end == 0) end = total / count;
        return end - 0.001F;
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        if (world.isAirBlock(pos)) return true;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == this || block instanceof BlockLiquid) return false;
        if (displacements.containsKey(block)) {
            if (displacements.get(block)) {
                if (state.getBlock() != Blocks.SNOW_LAYER)
                    block.dropBlockAsItem(world, pos, state, 0);
                return true;
            }
            return false;
        }
        Material material = state.getMaterial();
        if (material.blocksMovement() || material == Material.PORTAL) {
            return false;
        }
        int density = getDensity(world, pos);
        if (density == Integer.MAX_VALUE) {
            block.dropBlockAsItem(world, pos, state, 0);
            return true;
        }
        return this.density > density;
    }

    @Override
    public int getQuantaValue(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.AIR) return 0;
        if (state.getBlock() instanceof BlockLiquid) return quantaPerBlock - state.getValue(BlockLiquid.LEVEL);
        if (state.getBlock() != this) return -1;
        return quantaPerBlock - state.getValue(LEVEL);
    }
}


