package phoenix.utils.entity;

import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ThreeDimensionsMoveHelperController extends MovementController {
    private float speedFactor = 0.1F;
    AbstractFlyingEntity entity;
    public ThreeDimensionsMoveHelperController(AbstractFlyingEntity entityIn)
    {
        super(entityIn);
        entity = entityIn;
    }

    public void tick() {
        if (entity.collidedHorizontally) {
            entity.rotationYaw += 180.0F;
            this.speedFactor = 0.1F;
        }

        float f = (float)(entity.orbitOffset.x - entity.getPosX());
        float f1 = (float)(entity.orbitOffset.y - entity.getPosY());
        float f2 = (float)(entity.orbitOffset.z - entity.getPosZ());
        double d0 = MathHelper.sqrt(f * f + f2 * f2);
        double d1 = 1.0D - (double)MathHelper.abs(f1 * 0.7F) / d0;
        f = (float)((double)f * d1);
        f2 = (float)((double)f2 * d1);
        d0 = MathHelper.sqrt(f * f + f2 * f2);
        double d2 = MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
        float f3 = entity.rotationYaw;
        float f4 = (float)MathHelper.atan2((double)f2, (double)f);
        float f5 = MathHelper.wrapDegrees(entity.rotationYaw + 90.0F);
        float f6 = MathHelper.wrapDegrees(f4 * (180F / (float)Math.PI));
        entity.rotationYaw = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
        entity.renderYawOffset = entity.rotationYaw;
        if (MathHelper.degreesDifferenceAbs(f3, entity.rotationYaw) < 3.0F) {
            this.speedFactor = MathHelper.approach(this.speedFactor, 1.8F, 0.005F * (1.8F / this.speedFactor));
        } else {
            this.speedFactor = MathHelper.approach(this.speedFactor, 0.2F, 0.025F);
        }

        float f7 = (float)(-(MathHelper.atan2((double)(-f1), d0) * (double)(180F / (float)Math.PI)));
        entity.rotationPitch = f7;
        float f8 = entity.rotationYaw + 90.0F;
        double d3 = (double)(this.speedFactor * MathHelper.cos(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
        double d4 = (double)(this.speedFactor * MathHelper.sin(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
        double d5 = (double)(this.speedFactor * MathHelper.sin(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
        Vector3d vector3d = entity.getMotion();
        entity.setMotion(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
    }
}