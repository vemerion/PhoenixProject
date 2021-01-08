package phoenix.utils.entity

import net.minecraft.entity.MobEntity
import net.minecraft.entity.ai.controller.LookController
import net.minecraft.entity.ai.controller.MovementController
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.vector.Vector3d

class ThreeDimensionsLookHelperController(entityIn: MobEntity?) : LookController(entityIn)
{
    /**
     * Updates look
     */
    override fun tick()
    {
    }
}

class ThreeDimensionsMoveHelperController(var entity: AbstractFlyingEntity) : MovementController(
    entity
)
{
    private var speedFactor = 0.1f
    override fun tick()
    {
        if (entity.collidedHorizontally)
        {
            entity.rotationYaw += 180.0f
            speedFactor = 0.1f
        }
        var f = (entity.orbitOffset.x - entity.posX).toFloat()
        val f1 = (entity.orbitOffset.y - entity.posY).toFloat()
        var f2 = (entity.orbitOffset.z - entity.posZ).toFloat()
        var d0 = MathHelper.sqrt(f * f + f2 * f2).toDouble()
        val d1 = 1.0 - MathHelper.abs(f1 * 0.7f).toDouble() / d0
        f = (f.toDouble() * d1).toFloat()
        f2 = (f2.toDouble() * d1).toFloat()
        d0 = MathHelper.sqrt(f * f + f2 * f2).toDouble()
        val d2 = MathHelper.sqrt(f * f + f2 * f2 + f1 * f1).toDouble()
        val f3 = entity.rotationYaw
        val f4 = MathHelper.atan2(f2.toDouble(), f.toDouble()).toFloat()
        val f5 = MathHelper.wrapDegrees(entity.rotationYaw + 90.0f)
        val f6 = MathHelper.wrapDegrees(f4 * (180f / Math.PI.toFloat()))
        entity.rotationYaw = MathHelper.approachDegrees(f5, f6, 4.0f) - 90.0f
        entity.renderYawOffset = entity.rotationYaw
        if (MathHelper.degreesDifferenceAbs(f3, entity.rotationYaw) < 3.0f)
        {
            speedFactor = MathHelper.approach(speedFactor, 1.8f, 0.005f * (1.8f / speedFactor))
        } else
        {
            speedFactor = MathHelper.approach(speedFactor, 0.2f, 0.025f)
        }
        val f7 = (-(MathHelper.atan2((-f1).toDouble(), d0) * (180f / Math.PI.toFloat()).toDouble())).toFloat()
        entity.rotationPitch = f7
        val f8 = entity.rotationYaw + 90.0f
        val d3 = (speedFactor * MathHelper.cos(f8 * (Math.PI.toFloat() / 180f))).toDouble() * Math.abs(
            f.toDouble() / d2
        )
        val d4 = (speedFactor * MathHelper.sin(f8 * (Math.PI.toFloat() / 180f))).toDouble() * Math.abs(
            f2.toDouble() / d2
        )
        val d5 = (speedFactor * MathHelper.sin(f7 * (Math.PI.toFloat() / 180f))).toDouble() * Math.abs(
            f1.toDouble() / d2
        )
        val vector3d = entity.motion
        entity.motion = vector3d.add(Vector3d(d3, d5, d4).subtract(vector3d).scale(0.2))
    }
}

enum class AttackPhases
{
    CIRCLE, SWOOP
}
