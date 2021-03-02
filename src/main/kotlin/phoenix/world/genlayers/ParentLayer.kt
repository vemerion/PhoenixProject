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
        val res: Int
        val realX = x shr 2
        val realZ = z shr 2
        res = if (realX.toLong() * realX.toLong() + realZ.toLong() * realZ.toLong() <= 1024L) //if dragon island
        {
            0
        } else
        {
            val height = net.minecraft.world.biome.provider.EndBiomeProvider.getRandomNoise(provider.generator, realX * 2 + 1, realZ * 2 + 1)
            if (height > 40.0f)
            {
                3
            } else if (height >= 0.0f)
            {
                2
            } else
            {
                if (height < -20.0f) 4 else 1
            }
        }
        return res
    }

    override fun apply(context: INoiseRandom, x: Int, z: Int): Int
    {
        return apply(x, z, provider)
    }
}

val END_BARRENS       = 43 //Biomes.END_BARRENS      .getId()
val END_HIGHLANDS     = 42 //Biomes.END_HIGHLANDS    .getId()
val END_MIDLANDS      = 41 //Biomes.END_MIDLANDS     .getId()
val THE_END           = 9 //Biomes.THE_END          .getId()
val SMALL_END_ISLANDS = 40 //Biomes.SMALL_END_ISLANDS.getId()
val UNDER                = PhoenixBiomes.UNDER.getId()
val UNDER_SMALL_ISLANDS  = PhoenixBiomes.UNDER_ISLANDS.getId()
val HEART_VOID           = PhoenixBiomes.HEART_VOID.getId()