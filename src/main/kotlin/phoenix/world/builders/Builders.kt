package phoenix.world.builders

import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.util.registry.WorldGenRegistries.CONFIGURED_SURFACE_BUILDER
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import phoenix.init.PhoenixBlocks

object Builders
{
    //Конфиги для билдеров
    val UNDER_CONFIG = AdvancedSurfaceBuilderConfig(Blocks.END_STONE, Blocks.END_STONE, Blocks.END_STONE, PhoenixBlocks.FERTILE_END_STONE)
    val HEARTVOID_CONFIG = SurfaceBuilderConfig(Blocks.END_STONE.defaultState, Blocks.END_STONE.defaultState, Blocks.END_STONE.defaultState)

    //Билдеры
    val UNDER: SurfaceBuilder<AdvancedSurfaceBuilderConfig> = register("under", UnderSurfaceBuilder(AdvancedSurfaceBuilderConfig.CODEC))
    val HEARTVOID: SurfaceBuilder<SurfaceBuilderConfig>     = register("heart", HeartVoidSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_))

    val UNDER_CONF: ConfiguredSurfaceBuilder<AdvancedSurfaceBuilderConfig> = register("under", ConfiguredSurfaceBuilder(UnderSurfaceBuilder(AdvancedSurfaceBuilderConfig.CODEC), UNDER_CONFIG))
    val HEARTVOID_CONF: ConfiguredSurfaceBuilder<SurfaceBuilderConfig>     = register("heart", ConfiguredSurfaceBuilder(HeartVoidSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_), HEARTVOID_CONFIG))

    private fun <C : ISurfaceBuilderConfig, F : SurfaceBuilder<C>> register(key: String, builderIn: F): F =  Registry.register(Registry.SURFACE_BUILDER, key, builderIn)
    private fun <C : ISurfaceBuilderConfig, F : ConfiguredSurfaceBuilder<C>> register(key: String, builderIn: F): F = Registry.register(CONFIGURED_SURFACE_BUILDER, key, builderIn)
}