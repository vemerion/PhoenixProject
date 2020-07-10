package phoenix.client.models.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelPilons extends ModelBase
{
    private final ModelRenderer rightWing = new ModelRenderer(this, 0, 0);
    private final ModelRenderer leftWing  = new ModelRenderer(this, 0, 6);

    public ModelPilons()
    {
        this.leftWing.addBox(-20.0F, 0.0F, -5.0F, 20, 5, 1);
        this.rightWing.addBox(0.0F,  0.0F, -5.0F, 20, 5, 1);
         leftWing.setRotationPoint(0,  0, 5);
        rightWing.setRotationPoint(0, 0, 5);
        this.rightWing.mirror = true;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableCull();
         leftWing.rotateAngleY =  60;
        rightWing.rotateAngleY = -60;
        if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).isChild())
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 1.5F, -0.1F);
            this.leftWing.render(scale);
            this.rightWing.render(scale);
            GlStateManager.popMatrix();
        }
        else
        {
            this.leftWing.render(scale);
            this.rightWing.render(scale);
        }
    }
}
