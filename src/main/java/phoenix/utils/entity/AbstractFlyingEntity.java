package phoenix.utils.entity;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractFlyingEntity  extends FlyingEntity
{
    public Vec3d orbitOffset = new Vec3d();
    public BlockPos orbitPosition = BlockPos.ZERO;
    public AttackPhases attackPhase = AttackPhases.CIRCLE;
    protected AbstractFlyingEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
