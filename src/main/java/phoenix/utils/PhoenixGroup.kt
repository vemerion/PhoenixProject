package phoenix.utils

import net.minecraft.block.Blocks
import net.minecraft.item.*
import net.minecraft.util.NonNullList
import java.util.*

class PhoenixGroup(name: String) : ItemGroup(name)
{
    override fun createIcon(): ItemStack = ItemStack(Blocks.END_PORTAL_FRAME)

    override fun hasSearchBar() = true

    override fun isAlignedRight() = true

    override fun fill(items: NonNullList<ItemStack>)
    {
        items.sortWith(ItemStackComparator)
        super.fill(items)
    }

    override fun getSlotColor() = 0


    object ItemStackComparator : Comparator<ItemStack>
    {
        override fun compare(i1: ItemStack, i2: ItemStack): Int
        {
            var f = 0
            var s = 0
            when (i1.item)
            {
                is ArmorItem    -> f = 2
                is SwordItem    -> f = 3
                is AxeItem      -> f = 4
                is PickaxeItem  -> f = 5
                is ToolItem     -> f = 1
                is BlockItem    -> f = 6
            }

            when (i2.item)
            {
                is ArmorItem   -> s = 2
                is SwordItem   -> s = 3
                is AxeItem     -> s = 4
                is PickaxeItem -> s = 5
                is ToolItem    -> s = 6
                is BlockItem   -> s = 7
            }
            return f.compareTo(s)
        }
    }
}

