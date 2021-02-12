package phoenix.init

import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.enity.KnifeEntity
import phoenix.enity.TalpaEntity
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixEntities
{
    val ENTITIES = KDeferredRegister(ForgeRegistries.ENTITIES, Phoenix.MOD_ID)

    val TALPA: EntityType<TalpaEntity> by ENTITIES.register("talpa")
    {
        EntityType.Builder.create(::TalpaEntity, EntityClassification.CREATURE)
                .size(0.5f, 0.5f)
                .setTrackingRange(80)
                .setUpdateInterval(3)
                .setShouldReceiveVelocityUpdates(true)
                .build(ResourceLocation(Phoenix.MOD_ID, "talpa").toString())
    }

    /*
    val CAUDA = ENTITIES.register("cauda")
    {
        EntityType.Builder.create(::CaudaEntity, EntityClassification.CREATURE)
                .size(0.9f, 0.5f)
                .setTrackingRange(80)
                .setUpdateInterval(3)
                .setShouldReceiveVelocityUpdates(true)
                .build(ResourceLocation(Phoenix.MOD_ID, "cauda").toString())
    }!!
     */

    val KNIFE: EntityType<KnifeEntity> by ENTITIES.register("zirconium_knife")
    {
        EntityType.Builder.create(::KnifeEntity, EntityClassification.MISC)
                .size(0.1f, 0.1f)
                .setTrackingRange(80)
                .setUpdateInterval(1)
                .setShouldReceiveVelocityUpdates(true)
                .build(ResourceLocation(Phoenix.MOD_ID, "zirconium_knife").toString())
    }
}