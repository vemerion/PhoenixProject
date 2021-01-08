package phoenix.init

import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.world.structures.remains.RemainsStructure

object PhoenixFeatures
{
    val FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Phoenix.MOD_ID)

    val REMAINS: RegistryObject<Structure<NoFeatureConfig>> = FEATURES.register("remains", ::RemainsStructure)

    // public static final RegistryObject<CustomEndSpike>             END_SPIKE   = FEATURES.register("new_end_spike", CustomEndSpike::new);
    @JvmStatic
    fun register()
    {
        FEATURES.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}