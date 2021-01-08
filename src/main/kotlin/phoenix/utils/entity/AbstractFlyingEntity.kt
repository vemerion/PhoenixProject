package phoenix.utils.entity

import com.sun.javafx.geom.Vec3d
import net.minecraft.entity.EntityType
import net.minecraft.entity.FlyingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AbstractFlyingEntity protected constructor(type: EntityType<out FlyingEntity>, worldIn: World) : FlyingEntity(type, worldIn)
{
    var orbitOffset = Vec3d()
    var orbitPosition = BlockPos.ZERO
    var attackPhase = AttackPhases.CIRCLE
}
