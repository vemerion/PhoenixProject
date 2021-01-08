package phoenix.client.gui.diaryPages.elements

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import phoenix.containers.DiaryContainer

class RightAlignedTextElement : TextElement
{
    constructor(text: String) : super(text)
    constructor(text: String, color: Int) : super(text, color)

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
        super.render(stack, gui, font, xSize, ySize, xSize - font.getStringWidth(text.toString()), y, depth)
    }

    override fun toString(): String
    {
        return "[r]$text[R]"
    }
}