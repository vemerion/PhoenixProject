package phoenix.init

import net.minecraft.loot.LootTables
import net.minecraft.util.ResourceLocation
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import phoenix.Phoenix
import phoenix.world.structures.remains.RemainsPieces.Piece

object PhoenixLootTables
{
    lateinit var REMAINS : ResourceLocation

    fun init()
    {
        REMAINS = LootTables.register(ResourceLocation(Phoenix.MOD_ID, "remains_house"))
    }
}