package phoenix.client.render.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import phoenix.Phoenix;
import phoenix.client.models.blocks.ModelTank;
import phoenix.title.death.TileTank;

public class TankRender extends TileEntitySpecialRenderer<TileTank>
{
    boolean isChristmas = false;
    ResourceLocation TEXTURE_CHRISTMAS = new ResourceLocation(Phoenix.MOD_ID, "textures/blocks/tank_dead_new_year.png");
    ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Phoenix.MOD_ID, "textures/blocks/tank_dead_2.png");

    ResourceLocation TEXTURE_FLUID;

    @Override
    public void render(TileTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        try {
            TEXTURE_FLUID = new ResourceLocation(te.getTank().getFluid().getFluid().getStill().getNamespace(), "textures/" +  te.getTank().getFluid().getFluid().getStill().getPath() + ".png");
        }catch (Exception e){
            TEXTURE_FLUID = null;
        }
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        ModelTank modelchest = new ModelTank(te);
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else if (this.isChristmas) {
            this.bindTexture(TEXTURE_CHRISTMAS);
        }
        else {
            this.bindTexture(TEXTURE_NORMAL);
        }


        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        }

        GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);

        GlStateManager.rotate((float) 0, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        modelchest.base.render(1F / 16F * 0.9F);
        if(TEXTURE_FLUID != null) {
            bindTexture(TEXTURE_FLUID);
            modelchest.fluid.render(1F / 16F * 0.9F);
            modelchest.render();
            this.drawNameplate(te, "" + te.getTank().getFluidAmount(), te.getPos().getX(), te.getPos().getY() + 1, te.getPos().getZ(), 10);
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}

