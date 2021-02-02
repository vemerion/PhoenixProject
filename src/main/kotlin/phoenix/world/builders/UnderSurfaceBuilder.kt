package phoenix.world.builders

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import java.util.*
import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
open class UnderSurfaceBuilder(codec: Codec<AdvancedSurfaceBuilderConfig>) : SurfaceBuilder<AdvancedSurfaceBuilderConfig>(codec)
{
    private val WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultState
    private val ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.defaultState
    private val TERRACOTTA = Blocks.TERRACOTTA.defaultState
    private val YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.defaultState
    private val BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.defaultState
    private val RED_TERRACOTTA = Blocks.RED_TERRACOTTA.defaultState
    private val LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.defaultState
    override fun buildSurface(random: Random, chunkIn: IChunk, biomeIn: Biome, x: Int, z: Int, startHeight: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, seed: Long, config: AdvancedSurfaceBuilderConfig)
    {
        this.buildSurface(biomeIn, random, chunkIn, x, z, startHeight, noise, defaultBlock, config.top, config.under, config.getAdvanced())
    }

    private fun buildSurface(biomeIn: Biome,
        random: Random, chunkIn: IChunk, x: Int, z: Int, startHeight: Int, noise: Double,
        defaultBlock: BlockState, top: BlockState, middle: BlockState, under: BlockState
    )
    {
        val currentX = x and 15
        val currentZ = z and 15
        var blockstate = WHITE_TERRACOTTA
        val builderConfig: ISurfaceBuilderConfig = biomeIn.generationSettings.surfaceBuilderConfig
        val under = builderConfig.under
        val top = builderConfig.top
        var mutableState = under
        val k = (noise / 3.0 + 3.0 + random.nextDouble() * 0.25).toInt()
        val flag = Math.cos(noise / 3.0 * Math.PI) > 0.0
        var l = -1
        var flag1 = false
        var i1 = 0
        val currentPos = BlockPos.Mutable()

        for (j1 in startHeight downTo 0)
        {
            if (i1 < 15)
            {
                currentPos.setPos(currentX, j1, currentZ)
                val blockstate4 = chunkIn.getBlockState(currentPos)
                if (blockstate4.isAir)
                {
                    l = -1
                } else if (blockstate4.isIn(defaultBlock.block))
                {
                    if (l == -1)
                    {
                        flag1 = false
                        if (k <= 0)
                        {
                            blockstate = Blocks.AIR.defaultState
                            mutableState = defaultBlock
                        } else if (j1 >= 60 - 4 && j1 <= 60 + 1)
                        {
                            blockstate = WHITE_TERRACOTTA
                            mutableState = under
                        }
                        if (j1 < 60 && (blockstate.isAir))
                        {
                            blockstate = Blocks.WATER.defaultState
                        }
                        l = k + 0.coerceAtLeast(j1 - 60)
                        if (j1 >= 60 - 1)
                        {
                            if (j1 > 60 + 3 + k)
                            {
                                val toPlace: BlockState = if (j1 >= 64 && j1 <= 127)
                                {
                                    if (flag)
                                    {
                                        TERRACOTTA
                                    } else
                                    {
                                        Blocks.FARMLAND.defaultState
                                    }
                                } else
                                {
                                    ORANGE_TERRACOTTA
                                }
                                chunkIn.setBlockState(currentPos, toPlace, false)
                            } else
                            {
                                chunkIn.setBlockState(currentPos, top, false)
                                flag1 = true
                            }
                        } else
                        {
                            chunkIn.setBlockState(currentPos, mutableState, false)
                            val block = mutableState.block
                            if (block === Blocks.WHITE_TERRACOTTA || block === Blocks.ORANGE_TERRACOTTA || block === Blocks.MAGENTA_TERRACOTTA || block === Blocks.LIGHT_BLUE_TERRACOTTA || block === Blocks.YELLOW_TERRACOTTA || block === Blocks.LIME_TERRACOTTA || block === Blocks.PINK_TERRACOTTA || block === Blocks.GRAY_TERRACOTTA || block === Blocks.LIGHT_GRAY_TERRACOTTA || block === Blocks.CYAN_TERRACOTTA || block === Blocks.PURPLE_TERRACOTTA || block === Blocks.BLUE_TERRACOTTA || block === Blocks.BROWN_TERRACOTTA || block === Blocks.GREEN_TERRACOTTA || block === Blocks.RED_TERRACOTTA || block === Blocks.BLACK_TERRACOTTA)
                            {
                                chunkIn.setBlockState(currentPos, ORANGE_TERRACOTTA, false)
                            }
                        }
                    } else if (l > 0)
                    {
                        --l
                        if (flag1)
                        {
                            chunkIn.setBlockState(currentPos, ORANGE_TERRACOTTA, false)
                        } else
                        {
                            chunkIn.setBlockState(currentPos, Blocks.FARMLAND.defaultState, false)
                        }
                    }
                    ++i1
                }
            }
        }
    }
}
