package phoenix.client.gui.diaryPages.elements

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.util.text.ITextComponent
import phoenix.containers.DiaryContainer

class CenterAlignedTextElement : TextElement
{
    constructor(string : ITextComponent) : super(string)
    constructor(string : String) : super(string)
    constructor(string : String, color : Int) : super(string, color)

    override fun render(stack : MatrixStack, gui: ContainerScreen<DiaryContainer>, font: FontRenderer, xSize: Int, ySize: Int, x: Int, y: Int, depth: Int)
    {
        super.render(stack, gui, font, xSize, ySize, x +  xSize / 2 - font.getStringWidth(text.string) / 2, y, depth)
    }

    override fun toString(): String
    {
        return "[c]" + super.toString() + "[C]"
    }
}