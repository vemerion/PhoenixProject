package phoenix.world.builders

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import phoenix.init.PhoenixBlocks
import phoenix.utils.LogManager
import phoenix.utils.nextInt
import java.util.*
import javax.annotation.ParametersAreNonnullByDefault
import kotlin.math.min

@ParametersAreNonnullByDefault
open class UnderSurfaceBuilder(codec: Codec<AdvancedSurfaceBuilderConfig>) : SurfaceBuilder<AdvancedSurfaceBuilderConfig>(codec)
{
    override fun buildSurface(random: Random, chunkIn: IChunk, biomeIn: Biome, x: Int, z: Int, startHeight: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, seed: Long, config: AdvancedSurfaceBuilderConfig)
    {
        this.buildSurface(biomeIn, random, chunkIn, x, z)
    }

    private fun buildSurface(biomeIn: Biome, random: Random, chunkIn: IChunk, x: Int, z: Int)
    {
        val currentX = x and 15
        val currentZ = z and 15
        for (y in 0..22 + random.nextInt(0, 5))
        {
            val pos = BlockPos(currentX, y, currentZ)
            if (chunkIn.getBlockState(pos).block == Blocks.END_STONE)
            {
                chunkIn.setBlockState(pos, PhoenixBlocks.FERTILE_END_STONE.defaultState, false)
            }
        }
    }
}
