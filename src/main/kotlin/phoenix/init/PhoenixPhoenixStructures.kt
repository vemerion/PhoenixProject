package phoenix.init

import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.world.structures.remains.RemainsPieces
import phoenix.world.structures.remains.RemainsStructure
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixStructures
{
    val FEATURES = KDeferredRegister(ForgeRegistries.STRUCTURE_FEATURES, Phoenix.MOD_ID)
    lateinit var REMAINS_PIECES : IStructurePieceType

    val REMAINS by FEATURES.register("remains", ::RemainsStructure)
    // public static final RegistryObject<CustomEndSpike>             END_SPIKE   = FEATURES.register("new_end_spike", CustomEndSpike::new);

    fun initPieces()
    {
        REMAINS_PIECES = IStructurePieceType.register(RemainsPieces::Piece, "RemainsHouse")
    }
}