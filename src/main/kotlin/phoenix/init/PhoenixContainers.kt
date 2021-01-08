package phoenix.init

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.containers.DiaryContainer
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixContainers
{
    val CONTAINERS = KDeferredRegister(ForgeRegistries.CONTAINERS, Phoenix.MOD_ID)
    val GUIDE by CONTAINERS.register("diary") { DiaryContainer.fromNetwork() }

    @OnlyIn(Dist.CLIENT)
    fun registerScreens()
    {
        //ScreenManager.registerFactory(GUIDE.get(), DiaryGui::new);
    }
}