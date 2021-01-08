package phoenix.utils

import java.util.*


object ArrayUtils
{
    fun <T> part(list: ArrayList<T>, from: Int, to: Int): ArrayList<T>
    {
        val res = ArrayList<T>()
        for (i in from until to)
        {
            res.add(list[getIndex(list.size, i)])
        }
        return res
    }

    fun <T> part(list: LinkedList<T>, from: Int, to: Int): ArrayList<T>
    {
        val res = ArrayList<T>()
        var i = from
        while (i < to && i < list.size)
        {
            res.add(list[getIndex(list.size, i)])
            i++
        }
        return res
    }

    private fun getIndex(size: Int, indexIn: Int): Int
    {
        var index = indexIn
        if (size == 0) return 0
        while (index < 0) index += size
        return if (index >= size) size - 1 else index
    }
}
