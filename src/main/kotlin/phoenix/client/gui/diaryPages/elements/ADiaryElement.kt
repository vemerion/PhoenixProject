package phoenix.client.gui.diaryPages.elements

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.nbt.CompoundNBT
import phoenix.containers.DiaryContainer


abstract class ADiaryElement
{
    abstract fun getHeight(maxSizeXIn: Int, maxSizeYIn: Int): Int
    abstract fun render(
        stack: MatrixStack,
        gui: ContainerScreen<DiaryContainer>,
        renderer: FontRenderer,
        xSize: Int,
        ySize: Int,
        x: Int,
        y: Int,
        depth: Int
    )

    abstract fun serialize(): CompoundNBT
    abstract override fun toString(): String
}
