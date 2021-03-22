package phoenix.enity.boss.phase.phases.ash

import net.minecraft.entity.EntityPredicate
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import phoenix.enity.boss.AbstractEnderDragonEntity
import phoenix.enity.boss.phase.PhaseType
import kotlin.math.abs

class ScanningSittingPhase(dragonIn: AbstractEnderDragonEntity) : SittingPhase(dragonIn)
{
    private val predicate: EntityPredicate = EntityPredicate().setDistance(20.0).setCustomPredicate { entity: LivingEntity -> abs(entity.posY - dragonIn.posY) <= 10.0 }
    private var scanningTime = 0

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    override fun serverTick()
    {
        ++scanningTime
        var livingentity: LivingEntity? = dragon.world.getClosestPlayer(
            predicate,
            dragon, dragon.posX, dragon.posY, dragon.posZ
        )
        if (livingentity != null)
        {
            if (scanningTime > 25)
            {
                dragon.phaseManager.setPhase(PhaseType.SITTING_ATTACKING)
            } else
            {
                val vec3d = Vec3d(livingentity.posX - dragon.posX, 0.0, livingentity.posZ - dragon.posZ)
                    .normalize()
                val vec3d1 = Vec3d(
                    MathHelper.sin(dragon.rotationYaw * (Math.PI.toFloat() / 180f))
                        .toDouble(), 0.0, (-MathHelper.cos(dragon.rotationYaw * (Math.PI.toFloat() / 180f))).toDouble()
                ).normalize()
                val f = vec3d1.dotProduct(vec3d).toFloat()
                val f1 = (Math.acos(f.toDouble()) * (180f / Math.PI.toFloat()).toDouble()).toFloat() + 0.5f
                if (f1 < 0.0f || f1 > 10.0f)
                {
                    val d0 = livingentity.posX - dragon.dragonPartHead.posX
                    val d1 = livingentity.posZ - dragon.dragonPartHead.posZ
                    val d2 = MathHelper.clamp(
                        MathHelper.wrapDegrees(
                            180.0 - MathHelper.atan2(
                                d0,
                                d1
                            ) * (180f / Math.PI.toFloat()).toDouble() - dragon.rotationYaw.toDouble()
                        ), -100.0, 100.0
                    )
                    dragon.field_226525_bB_ *= 0.8f
                    var f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0f
                    val f3 = f2
                    if (f2 > 40.0f)
                    {
                        f2 = 40.0f
                    }
                    dragon.field_226525_bB_ =
                        (dragon.field_226525_bB_.toDouble() + d2 * (0.7f / f2 / f3).toDouble()).toFloat()
                    dragon.rotationYaw += dragon.field_226525_bB_
                }
            }
        } else if (scanningTime >= 100)
        {
            livingentity = dragon.world.getClosestPlayer(
                field_221115_b,
                dragon, dragon.posX, dragon.posY, dragon.posZ
            )
            dragon.phaseManager.setPhase(PhaseType.TAKEOFF)
            if (livingentity != null)
            {
                dragon.phaseManager.setPhase(PhaseType.CHARGING_PLAYER)
                dragon.phaseManager.getPhase<ChargingPlayerPhase>(PhaseType.CHARGING_PLAYER)?.setTarget(Vec3d(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ()))
            }
        }
    }

    /**
     * Called when this phase is set to active
     */
    override fun initPhase()
    {
        scanningTime = 0
    }

    override val type =  PhaseType.SITTING_SCANNING

    companion object
    {
        private val field_221115_b = EntityPredicate().setDistance(150.0)
    }

}
