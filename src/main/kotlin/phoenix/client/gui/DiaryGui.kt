package phoenix.client.gui

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.gui.widget.Widget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import phoenix.Phoenix
import phoenix.client.gui.diaryPages.Chapters
import phoenix.client.gui.diaryPages.DiaryBook
import phoenix.client.gui.diaryPages.elements.DiaryChapter
import phoenix.client.gui.diaryPages.elements.RightAlignedTextElement
import phoenix.containers.DiaryContainer
import phoenix.utils.DiaryUtils
import phoenix.utils.IChapterReader
import phoenix.utils.RenderUtils

class DiaryGui(screenContainer: DiaryContainer, inv: PlayerInventory, titleIn: ITextComponent) : ContainerScreen<DiaryContainer>(screenContainer, inv, titleIn)
{
    private val DIARY_TEXTURE = ResourceLocation(Phoenix.MOD_ID, "textures/gui/diary_container.png")
    private val DIARY = ResourceLocation(Phoenix.MOD_ID, "textures/gui/diary_2.png")
    private lateinit var book: DiaryBook
    val container: DiaryContainer = screenContainer

    override fun init()
    {
        super.init()
        addButton(InvisibleButton(guiLeft - 40, guiTop, ySize, { book-- }, true))
        addButton(InvisibleButton(guiLeft + xSize - 10, guiTop, ySize, { book++ }, true))
        book = DiaryBook(xSize - 30, ySize, Minecraft.getInstance().fontRenderer)
        val player = Minecraft.getInstance().player;
        val chapters = (player as IChapterReader).getOpenedChapters()
        for (i in chapters)
        {
            val ch = Chapters.values()[i.m];
            val els = DiaryUtils.makeParagraph(font, xSize, ch.getText())
            els.add(RightAlignedTextElement(i.v.toString()))
            book.add(DiaryChapter(xSize, ySize, els))
        }

    }

    override fun drawGuiContainerBackgroundLayer(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int)
    {
    }

    override fun render(stack : MatrixStack, mouseX : Int, mouseY : Int, partialTicks : Float)
    {
        super.render(stack, mouseX, mouseY, partialTicks)
        RenderUtils.drawRectScalable(DIARY, guiLeft, guiTop, xSize.toDouble(), ySize.toDouble(), blitOffset)
        book.render(stack, this, font, xSize, ySize, guiLeft, guiTop, blitOffset)
        for (button: Widget in buttons)
        {
            button.render(stack, mouseX, mouseY, partialTicks)
        }
    }

    init
    {
        xSize = (xSize * 1.8).toInt()
        ySize = (ySize * 1.8).toInt()
    }
}