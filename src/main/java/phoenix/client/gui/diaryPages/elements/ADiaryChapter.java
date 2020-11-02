package phoenix.client.gui.diaryPages.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import phoenix.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.LinkedList;

public class ADiaryChapter
{
    FontRenderer font;
    int xSize, ySize;
    public LinkedList<ADiaryElement> elements = new LinkedList<>();
    private int start = 0, end = 0, end2 = 0;

    public ADiaryChapter(int xSizeIn, int ySizeIn)
    {
        font = Minecraft.getInstance().fontRenderer;
        xSize = xSizeIn;
        ySize = ySizeIn;
    }


    public void add(ArrayList<ADiaryElement> elementsIn)
    {
        elements.addAll(elementsIn);
        recalculateSizes();
    }

    private void recalculateSizes()
    {
        int size  = 0;
        int count = 0;
        boolean isEnded = false;
        for (int i = 0; i < elements.size() && !isEnded; i++)
        {
            if ((size + elements.get(i).getHeight()) * (font.FONT_HEIGHT + 2) >= ySize - 30)
            {
                isEnded = true;
            }
            else
            {
                size += elements.get(i).getHeight();
                count++;
            }
        }
        start = 0;
        end   = count;

        size  = 0;
        count = 0;
        isEnded = false;
        for (int i = end; i < elements.size() && !isEnded; i++)
        {
            if ((size + elements.get(i).getHeight()) * font.FONT_HEIGHT >= ySize - 30)
            {
                isEnded = true;
            }
            else
            {
                size += elements.get(i).getHeight();
                count++;
            }
        }
        end2 = end + count + 1;
    }

    public boolean isLast()
    {
        return end >= elements.size() - 1 || end2 >= elements.size() - 1;
    }

    public boolean isFirst()
    {
        return start <= 0;
    }

    public ArrayList<ADiaryElement> getCurrentPage1()
    {
        return ArrayUtils.part(elements, start, end);
    }

    public ArrayList<ADiaryElement> getCurrentPage2()
    {
        return ArrayUtils.part(elements, end, end2);
    }

    public void next()
    {
        System.out.println(elements.size());
        if(!isLast())
        {
            int size = 0;
            int count = 0;
            boolean isEnded = false;
            for (int i = end2; i < elements.size() && !isEnded; i++)
            {
                if ((size + elements.get(i).getHeight()) * font.FONT_HEIGHT >= ySize - 30)
                {
                    isEnded = true;
                }
                size += elements.get(i).getHeight();
                count++;
            }
            start = end2 + 1;
            end = end2 + count + 1;

            size = 0;
            count = 0;
            isEnded = false;
            for (int i = end; i < elements.size() && !isEnded; i++)
            {
                if (size + elements.get(i).getHeight() >= 14)
                {
                    isEnded = true;
                }
                size += elements.get(i).getHeight();
                count++;
            }
            end2 = end + count + 1;
        }
    }

    public void prev()
    {
        System.out.println(elements.size());
        if(!isFirst())
        {
            int size = 0;
            int count = 0;
            boolean isEnded = false;
            for (int i = start - 1; i >= 0 && !isEnded; --i)
            {
                if ((size + elements.get(i).getHeight()) * font.FONT_HEIGHT >= ySize - 30)
                {
                    isEnded = true;
                } else
                {
                    size += elements.get(i).getHeight();
                    count++;
                }
            }
            end -= count + 1;
            end2 -= count + 1;

            size = 0;
            count = 0;
            isEnded = false;
            for (int i = end - 1; i >= 0 && !isEnded; --i)
            {
                if ((size + elements.get(i).getHeight()) * font.FONT_HEIGHT >= ySize - 30)
                {
                    isEnded = true;
                } else
                {
                    size += elements.get(i).getHeight();
                    count++;
                }
            }
            start = end - count - 1;
        }
    }
}