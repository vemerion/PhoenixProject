package phoenix.init

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.recipes.OvenRecipeSerializer
import phoenix.recipes.tank.TankRecipeSerializer

object PhoenixRecipeSerializers
{
    private val RS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Phoenix.MOD_ID)

    @JvmStatic val OVEN = RS.register("oven_recipe", ::OvenRecipeSerializer)!!
    @JvmStatic val TANK= RS.register("tank_recipe", ::TankRecipeSerializer)!!

    @JvmStatic
    fun register() = RS.register(FMLJavaModLoadingContext.get().modEventBus)
}