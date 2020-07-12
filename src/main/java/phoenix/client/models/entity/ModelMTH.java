package phoenix.client.models.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelMTH extends ModelBiped
{
    private final ModelRenderer rightWing = new ModelRenderer(this, 0, 0);
    private final ModelRenderer leftWing  = new ModelRenderer(this, 0, 6);
    private final ModelRenderer rightEar = new ModelRenderer(this, 0, 0);
    private final ModelRenderer leftEar  = new ModelRenderer(this, 0, 6);

    public ModelMTH()
    {
        leftWing.addBox(-20.0F, 0.0F, -5.0F, 20, 5, 1);
        rightWing.addBox(0.0F,  0.0F, -5.0F, 20, 5, 1);
        leftWing.setRotationPoint(0,  0, 5);
        rightWing.setRotationPoint(0, 0, 5);

        leftEar.addBox(-5.0F, -10.0F, -5.0F, 2, 1, 3);
        rightEar.addBox(5.0F, -10.0F, -5.0F, 2, 1, 3);
        leftEar.setRotationPoint(0,  0, 5);
        rightEar.setRotationPoint(0, 0, 5);

        this.rightWing.mirror = true;
        bipedBody.addChild(rightWing);
        bipedBody.addChild(leftWing);
        bipedHead.addChild(rightEar);
        bipedHead.addChild(leftEar);
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
            rightEar.render(scale);
            leftEar.render(scale);
            GlStateManager.popMatrix();
        }
        else
        {
            leftWing.render(scale);
            rightWing.render(scale);
            leftEar.render(scale);
            rightEar.render(scale);
        }
    }
}
