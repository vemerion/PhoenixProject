package phoenix.world.builders

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig

class AdvancedSurfaceBuilderConfig : ISurfaceBuilderConfig
{
    private val top: BlockState
    private val under: BlockState
    val underWater: BlockState
    val advanced: BlockState

    constructor(
        topMaterial: BlockState,
        underMaterial: BlockState,
        underWaterMaterial: BlockState,
        advancedMaterial: BlockState
    )
    {
        top = topMaterial
        under = underMaterial
        underWater = underWaterMaterial
        advanced = advancedMaterial
    }

    constructor(topMaterial: Block, underMaterial: Block, underWaterMaterial: Block, advancedMaterial: Block)
    {
        top = topMaterial.defaultState
        under = underMaterial.defaultState
        underWater = underWaterMaterial.defaultState
        advanced = advancedMaterial.defaultState
    }

    override fun getTop(): BlockState
    {
        return top
    }

    override fun getUnder(): BlockState
    {
        return under
    }

    companion object
    {
        val CODEC: Codec<AdvancedSurfaceBuilderConfig> = RecordCodecBuilder.create { kind: RecordCodecBuilder.Instance<AdvancedSurfaceBuilderConfig> ->
            kind.group(
                BlockState.CODEC.fieldOf("top").forGetter(AdvancedSurfaceBuilderConfig::top),
                BlockState.CODEC.fieldOf("under").forGetter(AdvancedSurfaceBuilderConfig::under),
                BlockState.CODEC.fieldOf("underWater").forGetter(AdvancedSurfaceBuilderConfig::underWater),
                BlockState.CODEC.fieldOf("advanced").forGetter(AdvancedSurfaceBuilderConfig::advanced)
            ).apply(kind, ::AdvancedSurfaceBuilderConfig)
        }
    }
}
