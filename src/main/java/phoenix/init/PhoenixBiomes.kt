package phoenix.init

import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary
import phoenix.utils.ResourceUtils

object PhoenixBiomes
{
    val UNDER = makeKey("under")
    val HEARTVOID = makeKey("heart_void")

    private fun makeKey(name: String): RegistryKey<Biome>
    {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ResourceUtils.key(name))
    }

    fun addBiomeTypes()
    {
        BiomeDictionary.addTypes(UNDER, BiomeDictionary.Type.END)
        BiomeDictionary.addTypes(HEARTVOID, BiomeDictionary.Type.END)
    }
}