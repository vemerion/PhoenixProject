package phoenix.init

import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.world.feature.SetaFeature
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixFeatures
{
    val FEATURES = KDeferredRegister(ForgeRegistries.FEATURES, Phoenix.MOD_ID)

    val SETA by FEATURES.register("seta") { SetaFeature }
//    val CONF_SETA = register("seta", SETA.withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG) as ConfiguredFeature<NoFeatureConfig, SetaFeature>)
}

private fun <FC : IFeatureConfig, T : Feature<FC>> register(key: String, configuredFeature: ConfiguredFeature<FC, T>): ConfiguredFeature<FC, T>
{
    return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature)
}
