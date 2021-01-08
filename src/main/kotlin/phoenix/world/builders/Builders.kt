package phoenix.world.builders

import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import phoenix.init.PhoenixBlocks

object Builders
{
    //Билдеры
    val UNDER: SurfaceBuilder<AdvancedSurfaceBuilderConfig> = register("under", UnderSurfaceBuilder(AdvancedSurfaceBuilderConfig.CODEC))
    val HEARTVOID: SurfaceBuilder<SurfaceBuilderConfig>     = register("heart", HeartVoidSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_))

    //Конфиги для билдеров
    val UNDER_CONFIG = AdvancedSurfaceBuilderConfig(
        Blocks.END_STONE,
        Blocks.END_STONE,
        Blocks.END_STONE,
        PhoenixBlocks.FERTILE_END_STONE
    )
    val HEARTVOID_CONFIG = SurfaceBuilderConfig(Blocks.END_STONE.defaultState, Blocks.END_STONE.defaultState, Blocks.END_STONE.defaultState)

    private fun <C : ISurfaceBuilderConfig, F : SurfaceBuilder<C>> register(key: String, builderIn: F): F
    {
        return Registry.register(Registry.SURFACE_BUILDER, key, builderIn)
    }
}