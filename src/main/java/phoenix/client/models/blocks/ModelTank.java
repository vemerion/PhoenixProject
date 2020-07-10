package phoenix.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.title.death.TileTank;

@SideOnly(Side.CLIENT)
public class ModelTank extends ModelBase
{

    public TileTank tileTank;
    public ModelRenderer base = new ModelRenderer(this, 0,0);
    public ModelRenderer fluid = new ModelRenderer(this, 0,0);
    public ModelTank(TileTank tankIn)
    {
        tileTank = tankIn;
         base.addBox(0, 0, 0, 16, 16, 16, 1);
         base.setRotationPoint(1, 1, 1);
         base.setTextureSize(16, 16);
        fluid.addBox(2, 2, 2, 12, 12 * tileTank.getTank().getFluidAmount() / tileTank.getTank().getCapacity(), 12, 1);
        fluid.setRotationPoint(1, 1, 1);
    }

    public void render()
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
        tessellator.draw();
    }
}
