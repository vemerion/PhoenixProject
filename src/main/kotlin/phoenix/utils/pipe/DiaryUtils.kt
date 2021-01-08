package phoenix.utils.pipe

import net.minecraft.client.gui.FontRenderer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import org.apache.commons.lang3.tuple.Pair
import phoenix.client.gui.diaryPages.elements.ADiaryElement
import phoenix.client.gui.diaryPages.elements.ImageElement
import phoenix.client.gui.diaryPages.elements.TextElement
import phoenix.utils.StringUtils
import phoenix.utils.TextureUtils
import java.util.*

object DiaryUtils
{
    //принимает ключи параграфов
    fun makeParagraphFromTranslate(xSizeIn: Int, font: FontRenderer, vararg keys: String): ArrayList<ADiaryElement>
    {
        return makeParagraph(font, xSizeIn, StringUtils.translateAll(*keys))
    }

    fun makeParagraph(font: FontRenderer, xSize: Int, text: Array<String>): ArrayList<ADiaryElement>
    {
        val res = ArrayList<ADiaryElement>()
        val words: MutableList<String> = ArrayList()
        for (current in text)  //проходим по всем параграфам
        {
            words.addAll(StringUtils.stringToWords(current))
            words.add("[break]")
        }
        var numberOfWords = 0
        while (numberOfWords < words.size)
        {
            var stringToAdd = "" //строка которую будем добавлять
            var nextWord = words[numberOfWords]
            while (font.getStringWidth("$stringToAdd $nextWord") < xSize / 2 - 30) //пока меньше ширины страницы
            {
                if (words[numberOfWords] == "\\n" || words[numberOfWords] == "[break]")
                {
                    res.add(TextElement(stringToAdd))
                    stringToAdd = ""
                } else
                {
                    stringToAdd += "$nextWord " //добавляем слово
                }
                ++numberOfWords
                nextWord = if (numberOfWords < words.size) words[numberOfWords] else break
            }
            res.add(TextElement(stringToAdd)) //добавляем строку
        }
        res.add(TextElement("")) //после каждого параграфа перенос
        return res
    }

    fun makeParagraph(font: FontRenderer, xSize: Int, text: ArrayList<String>): ArrayList<ADiaryElement>
    {
        val res = ArrayList<ADiaryElement>()
        val words: MutableList<String> = ArrayList()
        for (current in text)  //проходим по всем параграфам
        {
            words.addAll(StringUtils.stringToWords(current))
            words.add("[break]")
        }
        var numberOfWords = 0
        while (numberOfWords < words.size)
        {
            var stringToAdd = "" //строка которую будем добавлять
            var nextWord = words[numberOfWords]
            while (font.getStringWidth("$stringToAdd $nextWord") < xSize / 2 - 30) //пока меньше ширины страницы
            {
                if (words[numberOfWords] == "\\n" || words[numberOfWords] == "[break]")
                {
                    res.add(TextElement(stringToAdd))
                    stringToAdd = ""
                } else
                {
                    stringToAdd += "$nextWord " //добавляем слово
                }
                ++numberOfWords
                nextWord = if (numberOfWords < words.size) words[numberOfWords] else break
            }
            res.add(TextElement(stringToAdd)) //добавляем строку
        }
        res.add(TextElement("")) //после каждого параграфа перенос
        return res
    }

    fun add(chapter: ArrayList<ADiaryElement>, vararg toAdd: Pair<Int, ADiaryElement>): ArrayList<ADiaryElement>
    {
        for (pair in toAdd)
        {
            if (chapter.size < pair.left)
            {
                chapter.add(pair.right)
            } else
            {
                chapter.add(pair.left, pair.right)
            }
        }
        return chapter
    }

    fun readImageElement(nbt: CompoundNBT, maxSizeX: Int, maxSizeY: Int): ImageElement
    {
        val d = TextureUtils.getTextureSize(ResourceLocation(nbt.getString("res")))
        return ImageElement(ResourceLocation(nbt.getString("res")), d.key, d.value)
    }
}
