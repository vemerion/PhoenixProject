package phoenix.init

import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixSounds
{
    var SOUNDS = KDeferredRegister(ForgeRegistries.SOUND_EVENTS, Phoenix.MOD_ID)

    val CHANGE_STAGE    by SOUNDS.register("change_stage") { SoundEvent(ResourceLocation("change_stage")) }
    val UNDER_THE_WORLD by SOUNDS.register("under_the_world") { SoundEvent(ResourceLocation("under_the_world")) }
}