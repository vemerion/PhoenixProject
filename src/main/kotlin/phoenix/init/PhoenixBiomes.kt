package phoenix.init

import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.biome.BiomeMaker
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.utils.ResourceUtils
import phoenix.world.builders.HEARTVOID_CONF
import phoenix.world.builders.UNDER_CONF
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixBiomes
{
    val UNDER_KEY = makeKey("under")
    val HEARTVOID_KEY = makeKey("heart_void")

    private fun makeKey(name: String): RegistryKey<Biome> = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ResourceUtils.key(name))

    fun addBiomeTypes()
    {
        BiomeDictionary.addTypes(UNDER_KEY, BiomeDictionary.Type.END)
        BiomeDictionary.addTypes(HEARTVOID_KEY, BiomeDictionary.Type.END)
    }

    val BIOMES = KDeferredRegister(ForgeRegistries.BIOMES, Phoenix.MOD_ID)

    val UNDER      by BIOMES.register("under")      { BiomeMaker.makeEndBiome(BiomeGenerationSettings.Builder().withSurfaceBuilder { UNDER_CONF }.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, PhoenixFeatures.CONF_SETA)) }
    val HEART_VOID by BIOMES.register("heart_void") { BiomeMaker.makeEndBiome(BiomeGenerationSettings.Builder().withSurfaceBuilder { HEARTVOID_CONF }) }
}