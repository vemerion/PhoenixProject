package phoenix.world

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.SharedSeedRandom
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryLookupCodec
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.LazyAreaLayerContext
import net.minecraft.world.gen.SimplexNoiseGenerator
import net.minecraft.world.gen.layer.Layer
import net.minecraft.world.gen.layer.ZoomLayer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixConfiguration
import phoenix.utils.LogManager
import phoenix.world.genlayers.*
import java.util.*
import java.util.function.BiFunction

class EndBiomeProvider(private val lookupRegistry: Registry<Biome>, val seed: Long) : net.minecraft.world.biome.provider.EndBiomeProvider(
    lookupRegistry,
    seed
)
{
    val generator: SimplexNoiseGenerator
    private lateinit var genBiomes: Layer

    init
    {
        val biomesIn = ArrayList<Biome>()
        biomesIn.addAll(biomes)
        biomesIn.add(PhoenixBiomes.UNDER)
        biomesIn.add(PhoenixBiomes.HEART_VOID)
        this.biomes = biomesIn
        updateLayer()
        INSTANCE = this
        LogManager.error("It works!!")
    }

    @OnlyIn(Dist.CLIENT)
    override fun getBiomeProvider(seed: Long) = EndBiomeProvider(lookupRegistry, seed)

    override fun getNoiseBiome(x: Int, y: Int, z: Int) : Biome = genBiomes.func_242936_a(lookupRegistry, x, z)

    init
    {
        val rand = SharedSeedRandom(seed)
        rand.skip(17292)
        generator = SimplexNoiseGenerator(rand)
    }

    fun updateLayer()
    {
        val context = { dopSeed: Long -> LazyAreaLayerContext(25, seed, dopSeed) }

        var phoenixBiomes = ParentLayer(this).apply(context(1L))
        val vanilaBiomes  = ParentLayer(this).apply(context(1L))

        val stage = StageManager.stage

        if (stage >= 1)
        {
            phoenixBiomes = UnderLayer.apply(context(200L), phoenixBiomes)
            phoenixBiomes = HeartVoidLayer.apply(context(200L), phoenixBiomes)
            phoenixBiomes = UnderSmallIslandsLayer.apply(context(200L), phoenixBiomes)
        }

        for (i in 0..PhoenixConfiguration.biomeSize + 7) phoenixBiomes = ZoomLayer.NORMAL.apply(context(200L * i), phoenixBiomes)

        genBiomes = Layer(UnificationLayer.apply(context(100L), phoenixBiomes, vanilaBiomes))
    }

    companion object
    {
        lateinit var INSTANCE : EndBiomeProvider

        val CODEC: Codec<EndBiomeProvider> = RecordCodecBuilder.create { builder: RecordCodecBuilder.Instance<EndBiomeProvider> ->
            builder.group(
                RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(EndBiomeProvider::lookupRegistry),
                Codec.LONG.fieldOf("seed").stable().forGetter(EndBiomeProvider::seed)
            ).apply(
                builder, builder.stable(BiFunction<Registry<Biome>, Long, EndBiomeProvider> { lookupRegistry: Registry<Biome>, seed: Long -> EndBiomeProvider(lookupRegistry, seed) })
            )
        }

        fun getRandomNoise(generator: SimplexNoiseGenerator, x: Int, z: Int): Float
        {
            val realX = x / 2
            val realZ = z / 2
            val dopX = x % 2
            val dopZ = z % 2
            var lvt_7_1_ = 100.0f - MathHelper.sqrt((x * x + z * z).toFloat()) * 8.0f
            lvt_7_1_ = MathHelper.clamp(lvt_7_1_, -100.0f, 80.0f)
            for (lvt_8_1_ in -12..12)
            {
                for (lvt_9_1_ in -12..12)
                {
                    val lvt_10_1_ = (realX + lvt_8_1_).toLong()
                    val lvt_12_1_ = (realZ + lvt_9_1_).toLong()
                    if (lvt_10_1_ * lvt_10_1_ + lvt_12_1_ * lvt_12_1_ > 4096L && generator.getValue(
                            lvt_10_1_.toDouble(),
                            lvt_12_1_.toDouble()
                        ) < -0.8999999761581421
                    )
                    {
                        val lvt_14_1_ = (MathHelper.abs(lvt_10_1_.toFloat()) * 3439.0f + MathHelper.abs(
                            lvt_12_1_.toFloat()
                        ) * 147.0f) % 13.0f + 9.0f
                        val lvt_15_1_ = (dopX - lvt_8_1_ * 2).toFloat()
                        val lvt_16_1_ = (dopZ - lvt_9_1_ * 2).toFloat()
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