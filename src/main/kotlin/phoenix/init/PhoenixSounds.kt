package phoenix.init

import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.utils.register
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixSounds
{
    var SOUNDS = KDeferredRegister(ForgeRegistries.SOUND_EVENTS, Phoenix.MOD_ID)

    val CHANGE_STAGE       by SOUNDS.register("change_stage", phoenixSound("change_stage"))
    val PUT_SMTH_TO_BARREL by SOUNDS.register("put_smth_to_barrel", phoenixSound("put_smth_to_barrel"))
    val REDO_MUSIC         by SOUNDS.register("redo_music",  phoenixSound("redo_music"))

    private fun phoenixSound(nameIn: String) = SoundEvent(ResourceLocation(Phoenix.MOD_ID, nameIn))
}