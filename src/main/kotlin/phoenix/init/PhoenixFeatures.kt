package phoenix.init

import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.world.feature.SetaFeature
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixFeatures
{
    val FEATURES = KDeferredRegister(ForgeRegistries.FEATURES, Phoenix.MOD_ID)
    val SETA : SetaFeature by FEATURES.register("seta") { SetaFeature }
    var CONF_SETA : ConfiguredFeature<NoFeatureConfig, SetaFeature> = SetaFeature.withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).register("seta")
}

private fun <FC : IFeatureConfig, T : Feature<FC>> register(key: String, configuredFeature: ConfiguredFeature<FC, T>): ConfiguredFeature<FC, T>
{
    return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature)
}

private fun <FC : IFeatureConfig, T : Feature<FC>>  ConfiguredFeature<FC, T>.register(key: String): ConfiguredFeature<FC, T>
{
    return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, this)
}
