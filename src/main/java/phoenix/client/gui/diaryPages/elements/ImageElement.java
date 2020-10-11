package phoenix.client.gui.diaryPages.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;
import phoenix.Phoenix;
import phoenix.containers.DiaryContainer;
import phoenix.utils.TextureUtils;

import java.awt.*;

public class ImageElement implements IDiaryElement
{
    final ResourceLocation img;
    int w, h, xSize, ySize;
    public ImageElement(ResourceLocation img, int xSize, int ySize)
    {
        this.img = img;
        Dimension d = TextureUtils.getTextureSize(img);
        this.w = d.width;
        this.h = d.height;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    public int getHeight()
    {
        double scale = makeScale();
        double height = Math.ceil(scale * h / 15D);
        Phoenix.LOGGER.error("logged " + height + " " + scale * h);
        return (int) height;
    }
    public double makeScale()
    {
        double wi = xSize - 30,
            he = ySize * (xSize - 30) / (double) w;
        wi *= ySize / he;
        he = ySize;
        return he / ((double) h);
    }

    @Override
    public void render(ContainerScreen<DiaryContainer> gui, FontRenderer renderer, int xSize, int x, int y)
    {
        System.out.println(w + " " +  h);
        RenderSystem.pushMatrix();
        double scale = makeScale();
        RenderSystem.scaled(scale, scale, scale);

        Minecraft.getInstance().getTextureManager().bindTexture(img);
        gui.blit((int) (x / scale), (int)(y / scale), 0, 0, w, h);

        RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);
        RenderSystem.popMatrix();
    }
}