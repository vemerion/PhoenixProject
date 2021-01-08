package phoenix.client.gui.diaryPages.elements

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import phoenix.containers.DiaryContainer


open class TextElement : ADiaryElement
{
    var text: ITextComponent = StringTextComponent("")
    var color = TextFormatting.BLACK.color

    constructor(text: String)
    {
        this.text = StringTextComponent(text)
    }

    constructor(text: ITextComponent)
    {
        this.text = text
    }

    constructor(text: String, color: Int)
    {
        this.text = StringTextComponent(text)
        this.color = color
    }

    override fun getHeight(maxSizeXIn: Int, maxSizeYIn: Int): Int
    {
        return 1
    }

    override fun render(
        stack: MatrixStack,
        gui: ContainerScreen<DiaryContainer>,
        font: FontRenderer,
        xSize: Int,
        ySize: Int,
        x: Int,
        y: Int,
        depth: Int
    )
    {
        font.drawString(stack, text.toString(), (x + 15).toFloat(), (y + 15).toFloat(), color!!)
    }

    override fun toString(): String
    {
        return text.toString()
    }

    override fun serialize(): CompoundNBT
    {
        val res = CompoundNBT()
        res.putString("text", text.toString())
        res.putString("type", "string")
        return res
    }
}
