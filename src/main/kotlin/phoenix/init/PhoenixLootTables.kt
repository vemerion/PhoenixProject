package phoenix.init

import net.minecraft.loot.LootTables
import net.minecraft.util.ResourceLocation
import phoenix.Phoenix

object PhoenixLootTables
{
    lateinit var REMAINS : ResourceLocation

    fun init()
    {
        REMAINS = LootTables.register(ResourceLocation(Phoenix.MOD_ID, "remains_house"))
    }
}