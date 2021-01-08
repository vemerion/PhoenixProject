package phoenix.client.gui

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.audio.SimpleSound
import net.minecraft.client.audio.SoundHandler
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.StringTextComponent


class InvisibleButton(xIn: Int, yIn: Int, height: Int, pressable: IPressable, private val playTurnSound: Boolean) :
    Button(xIn, yIn, 40, height, StringTextComponent(""), pressable)
{
    override fun renderButton(stack: MatrixStack, mouseX: Int, mouseY: Int, particleTicks: Float)
    {
    }

    override fun playDownSound(soundHandler: SoundHandler)
    {
        if (playTurnSound)
        {
            soundHandler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f))
        }
    }
}