package phoenix.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class InvisibleButton extends Button
{
    private final boolean playTurnSound;

    public InvisibleButton(int xIn, int yIn, int height, IPressable pressable, boolean playTurnSound) {
        super(xIn, yIn, 40, height,  new StringTextComponent(""), pressable);
        this.playTurnSound = playTurnSound;
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float particleTicks)
    {
    }

    @Override
    public void playDownSound(SoundHandler soundHandler)
    {
        if (this.playTurnSound)
        {
            soundHandler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
        }
    }
}