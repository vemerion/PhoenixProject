package phoenix.init

import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.enchantments.TeleportationEnchant
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixEnchantments
{
    var ENCHANTMENTS = KDeferredRegister(ForgeRegistries.ENCHANTMENTS, Phoenix.MOD_ID)

    val TELEPORTATION by ENCHANTMENTS.register("teleportation", ::TeleportationEnchant)
}