package phoenix.init

import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.biome.BiomeMaker
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Features
import net.minecraft.world.gen.feature.structure.StructureFeatures
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
    val UNDER_ISLANDS_KEY = makeKey("under_small_islands")

    private fun makeKey(name: String): RegistryKey<Biome> = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ResourceUtils.key(name))

    fun addBiomeTypes()
    {
        BiomeDictionary.addTypes(UNDER_KEY, BiomeDictionary.Type.END)
        BiomeDictionary.addTypes(HEARTVOID_KEY, BiomeDictionary.Type.END)
        BiomeDictionary.addTypes(UNDER_ISLANDS_KEY, BiomeDictionary.Type.END)
    }

    val BIOMES = KDeferredRegister(ForgeRegistries.BIOMES, Phoenix.MOD_ID)

    val UNDER         by BIOMES.register("under")      { makeUnderBiome() }
    val HEART_VOID    by BIOMES.register("heart_void") { BiomeMaker.makeEndBiome(BiomeGenerationSettings.Builder().withSurfaceBuilder { HEARTVOID_CONF }) }
    val UNDER_ISLANDS by BIOMES.register("under_small_islands") { makeSmallEndIslandsUnderBiome() }
}

fun makeSmallEndIslandsUnderBiome(): Biome
{
    val res =
        BiomeGenerationSettings.Builder().withSurfaceBuilder{ UNDER_CONF }
            .withFeature(GenerationStage.Decoration.RAW_GENERATION, Features.END_ISLAND_DECORATED)
            .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, PhoenixFeatures.CONF_SETA)

    return BiomeMaker.makeEndBiome(res)
}

fun makeUnderBiome(): Biome
{
    val res =
        BiomeGenerationSettings.Builder()
            .withSurfaceBuilder { UNDER_CONF }
            .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, PhoenixFeatures.CONF_SETA)
            .withStructure(StructureFeatures.field_244151_q)
            .withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY)
            .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT)
    return BiomeMaker.makeEndBiome(res)
}