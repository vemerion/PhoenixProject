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
        var topBlock = config.top
        var middleBlock = config.under
        val currectPos = BlockPos.Mutable()
        var i = -1
        val noiseHeight = (noise / 3.0 + 3.0 + random.nextDouble() * 0.25).toInt()
        val currectX = x and 15
        val currectZ = z and 15
        for (y in startHeight downTo 0)
        {
            currectPos.setPos(currectX, y, currectZ)
            val currectState = chunkIn.getBlockState(currectPos)
            if (currectState.isAir)
            {
                i = -1
            } else if (currectState.block === defaultBlock.block)
            {
                if (!(isAir(chunkIn, currectPos.up(9))    || isAir(chunkIn, currectPos.down(9)) ||
                      isAir(chunkIn, currectPos.south(9)) || isAir(chunkIn, currectPos.north(9)) ||
                      isAir(chunkIn, currectPos.west(9))  || isAir(chunkIn, currectPos.east(9))))
                {
                    if (currectState.block === Blocks.END_STONE)
                    {
                        chunkIn.setBlockState(currectPos, PhoenixBlocks.ANTI_AIR.defaultState, false)
                    }
                } else if (i == -1)
                {
                    if (noiseHeight <= 0)
                    {
                        topBlock = Blocks.AIR.defaultState
                        middleBlock = defaultBlock
                    }
                    i = noiseHeight
                    chunkIn.setBlockState(currectPos, topBlock, false)
                } else if (i > 0)
                {
                    --i
                    chunkIn.setBlockState(currectPos, middleBlock, false)
                }
            }
        }
    }

    private inline fun isAir(chunk: IChunk, pos: BlockPos) = isAir(chunk.getBlockState(pos))
    private inline fun isAir(state: BlockState) = state.isAir && state.block !== PhoenixBlocks.ANTI_AIR
}