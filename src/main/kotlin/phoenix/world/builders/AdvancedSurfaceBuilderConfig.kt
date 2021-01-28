package phoenix.world.builders

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig

class AdvancedSurfaceBuilderConfig : ISurfaceBuilderConfig
{
    private val top: () -> Block
    private val under: () -> Block
    val underWater: () -> Block
    val advanced: () -> Block


    constructor(topMaterial: Block, underMaterial: Block, underWaterMaterial: Block, advancedMaterial: Block)
    {
        top = { topMaterial}
        under = { underMaterial }
        underWater = { underWaterMaterial }
        advanced = { advancedMaterial }
    }

    constructor(topMaterial: Block, underMaterial: Block, underWaterMaterial: Block, advancedMaterial: () -> Block)
    {
        top = { topMaterial}
        under = { underMaterial }
        underWater = { underWaterMaterial }
        advanced = advancedMaterial
    }

    constructor(topMaterial: BlockState, underMaterial: BlockState, underWaterMaterial: BlockState, advancedMaterial: BlockState)
    {
        top = { topMaterial.block }
        under = { underMaterial.block }
        underWater = { underWaterMaterial.block }
        advanced = { advancedMaterial.block }
    }


    override fun getTop() = top.invoke().defaultState

    override fun getUnder() = under.invoke().defaultState

    fun getUnderWater() = underWater.invoke().defaultState
    fun getAdvanced() = underWater.invoke().defaultState

    companion object
    {
        val CODEC: Codec<AdvancedSurfaceBuilderConfig> = RecordCodecBuilder.create { kind: RecordCodecBuilder.Instance<AdvancedSurfaceBuilderConfig> ->
            kind.group(
                BlockState.CODEC.fieldOf("top").forGetter(AdvancedSurfaceBuilderConfig::getTop),
                BlockState.CODEC.fieldOf("under").forGetter(AdvancedSurfaceBuilderConfig::getUnder),
                BlockState.CODEC.fieldOf("underWater").forGetter(AdvancedSurfaceBuilderConfig::getUnderWater),
                BlockState.CODEC.fieldOf("advanced").forGetter(AdvancedSurfaceBuilderConfig::getAdvanced)
            ).apply(kind, ::AdvancedSurfaceBuilderConfig)
        }
    }
}
