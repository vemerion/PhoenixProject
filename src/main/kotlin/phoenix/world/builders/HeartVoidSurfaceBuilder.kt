package phoenix.world.builders

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import phoenix.init.PhoenixBlocks
import java.util.*

class HeartVoidSurfaceBuilder(function: Codec<AdvancedSurfaceBuilderConfig>) : SurfaceBuilder<AdvancedSurfaceBuilderConfig>(function)
{
    override fun buildSurface(random: Random, chunkIn: IChunk, biomeIn: Biome, x: Int, z: Int, startHeight: Int, noise: Double,
                              defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, seed: Long, config: AdvancedSurfaceBuilderConfig)
    {
        val currectPos = BlockPos.Mutable()
        val currectX = x and 15
        val currectZ = z and 15
        for (y in startHeight downTo 0)
        {
            currectPos.setPos(currectX, y, currectZ)
            val currectState = chunkIn.getBlockState(currectPos)
            if (currectState.block === Blocks.END_STONE)
            {
                if (!(isAir(chunkIn, currectPos.up(9))    || isAir(chunkIn, currectPos.down(9)) ||
                      isAir(chunkIn, currectPos.south(9)) || isAir(chunkIn, currectPos.north(9)) ||
                      isAir(chunkIn, currectPos.west(9))  || isAir(chunkIn, currectPos.east(9))))
                {
                    chunkIn.setBlockState(currectPos, PhoenixBlocks.ANTI_AIR.defaultState, false)
                }
            }
        }
    }

    private inline fun isAir(chunk: IChunk, pos: BlockPos) = isAir(chunk.getBlockState(pos))
    private inline fun isAir(state: BlockState) = state.isAir && state.block !== PhoenixBlocks.ANTI_AIR
}