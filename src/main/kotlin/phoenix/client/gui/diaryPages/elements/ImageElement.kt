package phoenix.client.gui.diaryPages.elements

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import phoenix.containers.DiaryContainer
import phoenix.utils.RenderUtils.drawRectScalable

class ImageElement(val img: ResourceLocation, var w: Int, var h: Int) : ADiaryElement()
{
    override fun getHeight(maxSizeXIn: Int, maxSizeYIn: Int): Int
    {
        val scale = scale(maxSizeXIn / 2, maxSizeYIn / 2)
        return Math.ceil(h * scale).toInt()
    }

    fun scale(maxSizeX: Int, maxSizeY: Int): Double
    {
        if (w != 0 && h != 0)
        {
            var scale = maxSizeX / w.toDouble()
            val sizeX: Int = (w * scale).toInt()
            val sizeY: Int = (h * scale).toInt()
            if (maxSizeY < sizeY)
            {
                scale *= maxSizeY.toDouble() / sizeY
            }
            return scale
        } else
        {
           return 1.0
        }
    }

    override fun render(
        stack: MatrixStack,
        gui: ContainerScreen<DiaryContainer>,
        renderer: FontRenderer,
        xSize: Int,
        ySize: Int,
        x: Int,
        y: Int,
        depth: Int
    )
    {
        val scale = scale(xSize / 2, ySize / 2)
        RenderSystem.pushMatrix()
        RenderSystem.scaled(scale, scale, scale)
        Minecraft.getInstance().getTextureManager().bindTexture(img)
        drawRectScalable(img, x + 15, y + 15, xSize.toDouble(), ySize.toDouble(), depth)
        RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale)
        RenderSystem.popMatrix()
    }

    //*
    override fun toString() = img.toString()

    //*/
    override fun serialize(): CompoundNBT
    {
        val res = CompoundNBT()
        res.putString("type", "img")
        res.putString("res", img.path)
        //res.putInt("maxx", maxSizeX);
        //res.putInt("maxy", maxSizeY);
        return res
    }
}