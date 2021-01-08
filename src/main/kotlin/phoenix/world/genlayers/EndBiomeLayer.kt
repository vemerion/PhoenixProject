package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IC0Transformer
import org.apache.logging.log4j.LogManager

object EndBiomeLayer : IC0Transformer
{
    override fun apply(context: INoiseRandom, value: Int): Int
    {
        return when (value)
        {
            1    -> 0
            2    -> 0
            3    -> 0
            4    -> 0
            else -> 0
        }
    }

    private val LOGGER = LogManager.getLogger()
}
