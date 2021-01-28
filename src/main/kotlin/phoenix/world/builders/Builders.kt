package phoenix.world.builders

import net.minecraft.block.Blocks.END_STONE
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries.CONFIGURED_SURFACE_BUILDER
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.registries.ForgeRegistries
import phoenix.init.PhoenixBlocks
import phoenix.utils.LogManager
import phoenix.utils.validateRegistryName
import phoenix.world.builders.AdvancedSurfaceBuilderConfig.Companion.CODEC

typealias USB = UnderSurfaceBuilder
typealias HVSB = HeartVoidSurfaceBuilder
typealias ASBC = AdvancedSurfaceBuilderConfig
typealias ConfSurfBuilder<T> = ConfiguredSurfaceBuilder<T>

//Конфиги для билдеров
val UNDER_CONFIG     = ASBC(END_STONE, END_STONE, END_STONE, PhoenixBlocks::FERTILE_END_STONE)
val HEARTVOID_CONFIG = ASBC(END_STONE.defaultState, END_STONE.defaultState, END_STONE.defaultState, END_STONE.defaultState)

val UNDER = USB(CODEC)
val HEART_VOID = HVSB(CODEC)

lateinit var UNDER_CONF    : ConfSurfBuilder<ASBC>
lateinit var HEARTVOID_CONF: ConfSurfBuilder<ASBC>

fun <C : ISurfaceBuilderConfig, F : ConfSurfBuilder<C>> F.register(key: String): F = Registry.register(CONFIGURED_SURFACE_BUILDER, key, this)

fun registerBuilders(event : RegistryEvent.Register<SurfaceBuilder<*>>)
{
    if(event.registry == ForgeRegistries.SURFACE_BUILDERS)
    {
        LogManager.error("<registerBuilders> ", "registered")
        UNDER.validateRegistryName("under")
        HEART_VOID.validateRegistryName("heart_void")
        event.registry.register(UNDER)
        event.registry.register(HEART_VOID)
        UNDER_CONF      = ConfSurfBuilder(UNDER, UNDER_CONFIG).register("under")
        HEARTVOID_CONF  = ConfSurfBuilder(HEART_VOID, HEARTVOID_CONFIG).register("heart_void")
    }
}