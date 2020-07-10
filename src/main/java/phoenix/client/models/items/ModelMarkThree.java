package phoenix.client.models.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMarkThree extends ModelBiped
{
    private ModelRenderer RightArm1;

    private ModelRenderer LeftArm1;

    private ModelRenderer RightLeggs1;
    private ModelRenderer LeftLeggs1;

    private ModelRenderer RightBoots1;
    private ModelRenderer RightBoots2;
    private ModelRenderer RightBoots3;
    private ModelRenderer RightBoots4;

    private ModelRenderer LeftBoots1;
    private ModelRenderer LeftBoots2;
    private ModelRenderer LeftBoots3;
    private ModelRenderer LeftBoots4;

    private ModelRenderer Torso;
    private ModelRenderer TorsoArmor1;
    private ModelRenderer TorsoArmor2;

    public ModelMarkThree(int type)
    {
        super(0, 0, 128, 128);

        RightArm1 = new ModelRenderer(this, 0, 0);
        RightArm1.addBox(-3.5F, -2F, -3.5F, 4, 12, 4);
        RightArm1.setRotationPoint(0F, 0F, 0F);
        RightArm1.setTextureSize(128, 128);
        RightArm1.mirror = true;
        setRotation(RightArm1, 0F, 0F, 0F);

        LeftArm1 = new ModelRenderer(this, 0, 0);
        LeftArm1.addBox(-1.5F, -2F, -3.5F, 4, 12, 4);
        LeftArm1.setRotationPoint(0F, 0F, 0F);
        LeftArm1.setTextureSize(128, 128);
        LeftArm1.mirror = true;
        setRotation(LeftArm1, 0F, 0F, 0F);

        RightLeggs1 = new ModelRenderer(this, 0, 0);
        RightLeggs1.addBox(-2.5F, -1F, -2.5F, 4, 12, 4);
        RightLeggs1.setRotationPoint(0F, 0F, 0F);
        RightLeggs1.setTextureSize(128, 128);
        RightLeggs1.mirror = true;
        setRotation(RightLeggs1, 0F, 0F, 0F);

        LeftLeggs1 = new ModelRenderer(this, 0, 0);
        LeftLeggs1.addBox(-2.5F, -1F, -2.5F, 4, 12, 4);
        LeftLeggs1.setRotationPoint(0F, 0F, 0F);
        LeftLeggs1.setTextureSize(128, 128);
        LeftLeggs1.mirror = true;
        setRotation(LeftLeggs1, 0F, 0F, 0F);

        RightBoots1 = new ModelRenderer(this, 0, 0);
        RightBoots1.addBox(-3F, 7F, -3F, 6, 5, 6);
        RightBoots1.setRotationPoint(0F, 0F, 0F);
        RightBoots1.setTextureSize(128, 128);
        RightBoots1.mirror = true;
        setRotation(RightBoots1, 0F, 1.570796F, 0F);

        RightBoots2 = new ModelRenderer(this, 0, 0);
        RightBoots2.addBox(-3F, 11.5F, -4F, 6, 1, 2);
        RightBoots2.setRotationPoint(0F, 0F, 0F);
        RightBoots2.setTextureSize(128, 128);
        RightBoots2.mirror = true;
        setRotation(RightBoots2, 0F, 0F, 0F);

        RightBoots3 = new ModelRenderer(this, 0, 0);
        RightBoots3.addBox(-3F, 6.5F, 7.5F, 6, 3, 1);
        RightBoots3.setRotationPoint(0F, 0F, 0F);
        RightBoots3.setTextureSize(128, 128);
        RightBoots3.mirror = true;
        setRotation(RightBoots3, -1.012291F, 0F, 0F);

        RightBoots4 = new ModelRenderer(this, 0, 0);
        RightBoots4.addBox(-3F, 11.5F, -3F, 6, 1, 6);
        RightBoots4.setRotationPoint(0F, 0F, 0F);
        RightBoots4.setTextureSize(128, 128);
        RightBoots4.mirror = true;
        setRotation(RightBoots4, 0F, 0F, 0F);

        LeftBoots1 = new ModelRenderer(this, 0, 0);
        LeftBoots1.addBox(-3F, 7F, -3F, 6, 5, 6);
        LeftBoots1.setRotationPoint(0F, 0F, 0F);
        LeftBoots1.setTextureSize(128, 128);
        LeftBoots1.mirror = true;
        setRotation(LeftBoots1, 0F, 1.570796F, 0F);

        LeftBoots2 = new ModelRenderer(this, 0, 0);
        LeftBoots2.addBox(-3F, 11.5F, -4F, 6, 1, 2);
        LeftBoots2.setRotationPoint(0F, 0F, 0F);
        LeftBoots2.setTextureSize(128, 128);
        LeftBoots2.mirror = true;
        setRotation(LeftBoots2, 0F, 0F, 0F);

        LeftBoots3 = new ModelRenderer(this, 0, 0);
        LeftBoots3.addBox(-3F, 6.5F, 7.5F, 6, 3, 1);
        LeftBoots3.setRotationPoint(0F, 0F, 0F);
        LeftBoots3.setTextureSize(128, 128);
        LeftBoots3.mirror = true;
        setRotation(LeftBoots3, -1.012291F, 0F, 0F);

        LeftBoots4 = new ModelRenderer(this, 0, 0);
        LeftBoots4.addBox(-3F, 11.5F, -3F, 6, 1, 6);
        LeftBoots4.setRotationPoint(0F, 0F, 0F);
        LeftBoots4.setTextureSize(128, 128);
        LeftBoots4.mirror = true;
        setRotation(LeftBoots4, 0F, 0F, 0F);

        Torso = new ModelRenderer(this, 0, 0);
        Torso.addBox(-4.5F, 9F, -2.5F, 9, 3, 5);
        Torso.setRotationPoint(0F, 0F, 0F);
        Torso.setTextureSize(128, 128);
        Torso.mirror = true;
        setRotation(Torso, 0F, 0F, 0F);

        TorsoArmor1 = new ModelRenderer(this, 0, 0);
        TorsoArmor1.addBox(-5F, 5F, -3F, 10, 8, 6);
        TorsoArmor1.setRotationPoint(0F, 0F, 0F);
        TorsoArmor1.setTextureSize(128, 128);
        TorsoArmor1.mirror = true;
        setRotation(TorsoArmor1, 0F, 0F, 0F);

        TorsoArmor2 = new ModelRenderer(this, 0, 0);
        TorsoArmor2.addBox(-5F, -0.5F, -3.5F, 10, 6, 7);
        TorsoArmor2.setRotationPoint(0F, 0F, 0F);
        TorsoArmor2.setTextureSize(128, 128);
        TorsoArmor2.mirror = true;
        setRotation(TorsoArmor2, 0F, 0F, 0F);

        this.bipedHead.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();

        //Тип брони 0 - голова, 1 - нагрудник, 2 - штаны, 3 - ботинки
        switch (type)
        {
            case 0: break;
            case 1:
                this.bipedLeftArm.addChild(LeftArm1);
                this.bipedRightArm.addChild(RightArm1);
                this.bipedBody.addChild(TorsoArmor1);
                this.bipedBody.addChild(TorsoArmor2);
                break;
            case 2:
                this.bipedRightLeg.addChild(RightLeggs1);
                this.bipedLeftLeg.addChild(LeftLeggs1);
                this.bipedBody.addChild(Torso);
                break;
            case 3:
                this.bipedRightLeg.addChild(RightBoots1);
                this.bipedLeftLeg.addChild(LeftBoots1);
                break;
        }
    }


    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale * 1.1F);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
