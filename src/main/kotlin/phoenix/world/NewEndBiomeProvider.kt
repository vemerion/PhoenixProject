package phoenix.world

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.SharedSeedRandom
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryLookupCodec
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.IExtendedNoiseRandom
import net.minecraft.world.gen.LazyAreaLayerContext
import net.minecraft.world.gen.SimplexNoiseGenerator
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.area.IAreaFactory
import net.minecraft.world.gen.layer.Layer
import net.minecraft.world.gen.layer.ZoomLayer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixConfiguration
import phoenix.world.genlayers.*
import java.util.*
import java.util.function.BiFunction
import java.util.function.LongFunction

class EndBiomeProvider(private val lookupRegistry: Registry<Biome>, val seed: Long) : net.minecraft.world.biome.provider.EndBiomeProvider(lookupRegistry, seed)
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
        genBiomes = createLayer(seed)
    }

    private fun createLayer(seed: Long): Layer
    {
        val res = getLayersApply { dopSeed: Long -> LazyAreaLayerContext(25, seed, dopSeed) }
        return Layer(res)
    }

    private fun <T : IArea, C : IExtendedNoiseRandom<T>> getLayersApply(context: LongFunction<C>): IAreaFactory<T>
    {
        var phoenixBiomes = ParentLayer(this).apply(context.apply(1L))

        val stage = StageManager.stage

        if (stage >= 1)
        {
            phoenixBiomes = UnderLayer.apply(context.apply(200L), phoenixBiomes)
            phoenixBiomes = HeartVoidLayer.apply(context.apply(200L), phoenixBiomes)
            phoenixBiomes = UnderSmallIslandsLayer.apply(context.apply(200L), phoenixBiomes)
        }

        //for (i in 0..PhoenixConfiguration.COMMON_CONFIG.BIOME_SIZE.get()) phoenixBiomes = ZoomLayer.NORMAL.apply(context.apply(200L), phoenixBiomes)
        //for (i in 0..3)                                                   phoenixBiomes = ZoomLayer.NORMAL.apply(context.apply(200L), phoenixBiomes)

        return UnificationLayer.apply(context.apply(100L), ParentLayer(this).apply(context.apply(1L)), phoenixBiomes)
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
    }
}
