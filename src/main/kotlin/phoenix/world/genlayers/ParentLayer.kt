package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IAreaTransformer0
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId
import phoenix.world.EndBiomeProvider

class ParentLayer(var provider: EndBiomeProvider) : IAreaTransformer0
{
    private fun apply(x: Int, z: Int, provider: EndBiomeProvider): Int
    {
        val realX = x shr 2
        val realZ = z shr 2
        return if (realX.toLong() * realX.toLong() + realZ.toLong() * realZ.toLong() <= 1024L) //if dragon island
        {
            THE_END
        } else
        {
            val height = EndBiomeProvider.getRandomNoise(provider.generator, realX * 2 + 1, realZ * 2 + 1)
            when
            {
                height > 40.0f  -> END_HIGHLANDS
                height >= 0.0f  -> END_MIDLANDS
                height < -20.0f -> SMALL_END_ISLANDS
                else            -> END_BARRENS
            }
        }
    }

    override fun apply(context: INoiseRandom, x: Int, z: Int): Int = apply(x, z, provider)
}

val END_BARRENS       = 43 //Biomes.END_BARRENS      .getId()
val END_HIGHLANDS     = 42 //Biomes.END_HIGHLANDS    .getId()
val END_MIDLANDS      = 41 //Biomes.END_MIDLANDS     .getId()
val THE_END           = 9  //Biomes.THE_END          .getId()
val SMALL_END_ISLANDS = 40 //Biomes.SMALL_END_ISLANDS.getId()
val UNDER                = PhoenixBiomes.UNDER.getId()
val UNDER_SMALL_ISLANDS  = PhoenixBiomes.UNDER_ISLANDS.getId()
val HEART_VOID           = PhoenixBiomes.HEART_VOID.getId()