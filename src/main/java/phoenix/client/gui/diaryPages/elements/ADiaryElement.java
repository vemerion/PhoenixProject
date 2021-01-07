package phoenix.client.gui.diaryPages.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.nbt.CompoundNBT;
import phoenix.containers.DiaryContainer;

public abstract class ADiaryElement
{
    abstract public int getHeight(int maxSizeXIn, int maxSizeYIn);
    abstract public void render(MatrixStack stack, ContainerScreen<DiaryContainer> gui, FontRenderer renderer, int xSize, int ySize, int x, int y, int depth);
    abstract public CompoundNBT serialize();

    @Override
    abstract public String toString();
}
