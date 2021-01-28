package phoenix.world.genlayers

import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IC0Transformer
import org.apache.logging.log4j.LogManager
import phoenix.utils.getId

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

    private val LOGGER = LogManager.getLogger()
    private val END_BARRENS = Biomes.END_BARRENS.getId()
    private val END_HIGHLANDS = Biomes.END_HIGHLANDS.getId()
    private val END_MIDLANDS = Biomes.END_MIDLANDS.getId()
    private val THE_END = Biomes.THE_END.getId()
    private val SMALL_END_ISLANDS = Biomes.SMALL_END_ISLANDS.getId()
}
