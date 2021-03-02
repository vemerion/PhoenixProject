package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IC0Transformer

object EndBiomeLayer : IC0Transformer
{
    override fun apply(context: INoiseRandom, value: Int): Int
    {
        return when (value)
        {
            1    -> END_BARRENS
            2    -> END_MIDLANDS
            3    -> END_HIGHLANDS
            4    -> SMALL_END_ISLANDS
            else -> THE_END
        }
    }
}