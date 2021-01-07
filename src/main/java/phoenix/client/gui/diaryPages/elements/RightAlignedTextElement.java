package phoenix.client.gui.diaryPages.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import phoenix.containers.DiaryContainer;

public class RightAlignedTextElement extends TextElement
{
    public RightAlignedTextElement(String text)
    {
        super(text);
    }

    public RightAlignedTextElement(String text, Integer color)
    {
        super(text, color);
    }

    @Override
    public void render(MatrixStack stack, ContainerScreen<DiaryContainer> gui, FontRenderer font, int xSize, int ySize, int x, int y, int depth)
    {
        super.render(stack, gui, font, xSize, ySize, xSize - font.getStringWidth(text.toString()), y, depth);
    }

    @Override
    public String toString()
    {
        return "[r]" + text + "[R]";  
    }
}