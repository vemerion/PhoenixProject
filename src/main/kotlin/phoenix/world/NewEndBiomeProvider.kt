package phoenix.world

import com.google.common.collect.ImmutableList
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.SharedSeedRandom
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryLookupCodec
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.gen.IExtendedNoiseRandom
import net.minecraft.world.gen.LazyAreaLayerContext
import net.minecraft.world.gen.SimplexNoiseGenerator
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.area.IAreaFactory
import net.minecraft.world.gen.layer.Layer
import net.minecraft.world.gen.layer.ZoomLayer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import phoenix.init.PhoenixConfiguration
import phoenix.utils.LogManager
import phoenix.world.genlayers.EndBiomeLayer
import phoenix.world.genlayers.ParentLayer
import java.util.function.BiFunction
import java.util.function.LongFunction

class EndBiomeProvider(private val lookupRegistry: Registry<Biome>, val seed: Long) :
    BiomeProvider(
        ImmutableList.of(lookupRegistry.getOrThrow(Biomes.THE_END) as Biome,
            lookupRegistry.getOrThrow(Biomes.END_HIGHLANDS) as Biome,
            lookupRegistry.getOrThrow(Biomes.END_MIDLANDS) as Biome,
            lookupRegistry.getOrThrow(Biomes.SMALL_END_ISLANDS) as Biome,
            lookupRegistry.getOrThrow(Biomes.END_BARRENS) as Biome)
    )
{
    val generator: SimplexNoiseGenerator
    private val theEndBiome: Biome = lookupRegistry.getOrThrow(Biomes.THE_END) as Biome
    private val endHighlandsBiome: Biome = lookupRegistry.getOrThrow(Biomes.END_HIGHLANDS) as Biome
    private val endMidlandsBiome: Biome = lookupRegistry.getOrThrow(Biomes.END_MIDLANDS) as Biome
    private val smallEndIslandsBiome: Biome = lookupRegistry.getOrThrow(Biomes.SMALL_END_ISLANDS) as Biome
    private val endBarrensBiome: Biome = lookupRegistry.getOrThrow(Biomes.END_BARRENS) as Biome
    var genBiomes: Layer = createLayer(seed)
    override fun getBiomeProviderCodec() = CODEC

    @OnlyIn(Dist.CLIENT)
    override fun getBiomeProvider(seed: Long) = EndBiomeProvider(lookupRegistry, seed)

    override fun getNoiseBiome(x: Int, y: Int, z: Int) = genBiomes.func_242936_a(lookupRegistry, x, z)

    fun areProvidersEqual(seed: Long) = this.seed == seed

    init
    {
        val rand = SharedSeedRandom(seed)
        rand.skip(17292)
        generator = SimplexNoiseGenerator(rand)
    }

    fun createLayer(seed: Long): Layer
    {
        val res = getLayersApply { dopSeed: Long -> LazyAreaLayerContext(25, seed, dopSeed) }
        return Layer(res)
    }

    private fun <T : IArea, C : IExtendedNoiseRandom<T>> getLayersApply(context: LongFunction<C>): IAreaFactory<T>
    {
        var phoenixBiomes = ParentLayer(this).apply(context.apply(1L))
        var vanilaBiomes = ParentLayer(this).apply(context.apply(1L))
        vanilaBiomes = EndBiomeLayer.apply(context.apply(200L), vanilaBiomes)
        /*
        val stage = StageManager.getStage()

        LogManager.log(this, stage.toString())

        if (stage >= 1)
        {
            phoenixBiomes = UnderLayer.apply(context.apply(200L), phoenixBiomes)
            phoenixBiomes = HeartVoidLayer.apply(context.apply(200L), phoenixBiomes)
        }

        for (i in 0..PhoenixConfiguration.COMMON_CONFIG.BIOME_SIZE.get())
            phoenixBiomes = ZoomLayer.NORMAL.apply(context.apply(200L), phoenixBiomes)

         */
        return vanilaBiomes
    }


    companion object
    {
        val CODEC: Codec<EndBiomeProvider> =
            RecordCodecBuilder.create { builder: RecordCodecBuilder.Instance<EndBiomeProvider> ->
                builder.group(
                    RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY)
                        .forGetter(EndBiomeProvider::lookupRegistry),
                    Codec.LONG.fieldOf("seed").stable().forGetter { provider: EndBiomeProvider -> provider.seed }
                ).apply(builder, builder.stable(BiFunction<Registry<Biome>, Long, EndBiomeProvider> { reg: Registry<Biome>, seed: Long -> EndBiomeProvider(reg, seed) }))
            }


        fun getRandomNoise(noise: SimplexNoiseGenerator, x: Int, z: Int): Float
        {
            val lvt_3_1_ = x / 2
            val lvt_4_1_ = z / 2
            val lvt_5_1_ = x % 2
            val lvt_6_1_ = z % 2
            var lvt_7_1_ =
                100.0f - MathHelper.sqrt((x * x + z * z).toFloat()) * 8.0f
            lvt_7_1_ = MathHelper.clamp(lvt_7_1_, -100.0f, 80.0f)
            for (lvt_8_1_ in -12..12)
            {
                for (lvt_9_1_ in -12..12)
                {
                    val lvt_10_1_ = (lvt_3_1_ + lvt_8_1_).toLong()
                    val lvt_12_1_ = (lvt_4_1_ + lvt_9_1_).toLong()
                    if (lvt_10_1_ * lvt_10_1_ + lvt_12_1_ * lvt_12_1_ > 4096L && noise.getValue(
                            lvt_10_1_.toDouble(),
                            lvt_12_1_.toDouble()
                        ) < -0.8999999761581421
                    )
                    {
                        val lvt_14_1_ = (MathHelper.abs(lvt_10_1_.toFloat()) * 3439.0f + MathHelper.abs(
                            lvt_12_1_.toFloat()
                        ) * 147.0f) % 13.0f + 9.0f
                        val lvt_15_1_ = (lvt_5_1_ - lvt_8_1_ * 2).toFloat()
                        val lvt_16_1_ = (lvt_6_1_ - lvt_9_1_ * 2).toFloat()
                        var lvt_17_1_ =
                            100.0f - MathHelper.sqrt(lvt_15_1_ * lvt_15_1_ + lvt_16_1_ * lvt_16_1_) * lvt_14_1_
                        lvt_17_1_ = MathHelper.clamp(lvt_17_1_, -100.0f, 80.0f)
                        lvt_7_1_ = Math.max(lvt_7_1_, lvt_17_1_)
                    }
                }
            }
            return lvt_7_1_
        }
    }
}
