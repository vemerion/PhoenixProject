package phoenix.client.models.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTalpa extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer[] paws = new ModelRenderer[4];

    public ModelTalpa()
    {
        textureHeight = 32;
        textureWidth = 32;
        body = new ModelRenderer(this, 0, 0);
        body.addBox(0,0,0, 6, 6, 10);
        body.setRotationPoint(-2, 18, -2);
        paws[0] = new ModelRenderer(this, 0, 16);
        paws[1] = new ModelRenderer(this, 0, 16);
        paws[2] = new ModelRenderer(this, 0, 16);
        paws[3] = new ModelRenderer(this, 0, 16);

        paws[0].setRotationPoint(1  + 3, -2 + 2 + 20,  1);
        paws[1].setRotationPoint(-5 + 2, -2 + 2 + 20,  1);
        paws[2].setRotationPoint(-2 + 2, 1  + 3 + 20,  -5);
        paws[3].setRotationPoint(-2 + 2, -5 + 2 + 20,  -5);

        paws[0].addBox(0,0,0,1, 2, 6);
        paws[1].addBox(0,0,0,1, 2, 6);
        paws[2].addBox(0,0,0,2, 1, 6);
        paws[3].addBox(0,0,0,2, 1, 6);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        paws[0].rotateAngleY = -25;
        paws[1].rotateAngleY =  25;
        paws[2].rotateAngleX = -25;
        paws[3].rotateAngleX =  25;
        body.render(1F / 16F);
        for (ModelRenderer paw:paws) {
            paw.render(1F / 16F);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        paws[0].offsetZ = paws[1].offsetZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F / 4;
        paws[3].offsetZ = paws[2].offsetZ =  MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / 4;
    }
}
