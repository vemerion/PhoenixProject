package phoenix.world.genlayers

import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IAreaTransformer0
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixBlocks
import phoenix.utils.getId
import phoenix.world.EndBiomeProvider

class ParentLayer(var provider: EndBiomeProvider) : IAreaTransformer0
{
    override fun apply(context: INoiseRandom, x: Int, z: Int): Int
    {
        val realX = x shr 2
        val realZ = z shr 2
        return if (realX.toLong() * realX.toLong() + realZ.toLong() * realZ.toLong() <= 1024L) //if dragon island
        {
            THE_END
        } else
        {
            val height = net.minecraft.world.biome.provider.EndBiomeProvider.getRandomNoise(provider.generator, realX * 2 + 1, realZ * 2 + 1)
            if (height > 40.0f)
            {
                END_HIGHLANDS
            } else if (height >= 0.0f)
            {
                END_MIDLANDS
            } else
            {
                if (height < -20.0f) SMALL_END_ISLANDS else END_BARRENS
            }
        }
    }
}

val END_BARRENS       = Biomes.END_BARRENS      .getId()
val END_HIGHLANDS     = Biomes.END_HIGHLANDS    .getId()
val END_MIDLANDS      = Biomes.END_MIDLANDS     .getId()
val THE_END           = Biomes.THE_END          .getId()
val SMALL_END_ISLANDS = Biomes.SMALL_END_ISLANDS.getId()
val UNDER                = PhoenixBiomes.UNDER.getId()
val UNDER_SMALL_ISLANDS  = PhoenixBiomes.UNDER_ISLANDS.getId()
val HEART_VOID           = PhoenixBiomes.HEART_VOID.getId()