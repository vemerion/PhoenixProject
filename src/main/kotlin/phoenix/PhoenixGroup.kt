package phoenix

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem
import net.minecraft.util.IItemProvider
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.RegistryObject
import java.util.*
import kotlin.Comparator

class PhoenixGroup : ItemGroup
{
    var item : () -> IItemProvider
    constructor(name: String, item: IItemProvider) : super(name)
    {
       this.item = {item}
    }
    constructor(name: String, item: () -> IItemProvider) : super(name)
    {
        this.item = item
    }
    constructor(name: String, item: RegistryObject<Block>) : super(name)
    {
        this.item = item::get
    }

    override fun createIcon() = ItemStack(item.invoke())
    override fun hasSearchBar() = false

    override fun fill(items: NonNullList<ItemStack>)
    {
        super.fill(items)
        Collections.sort(items, ItemStackComparator)
    }

    internal object ItemStackComparator : Comparator<ItemStack>
    {
        override fun compare(i1: ItemStack, i2: ItemStack): Int
        {
            val f = getWeight(i1)
            val s = getWeight(i2)
            return when
            {
                f > s -> 1
                f < s -> -1
                else  ->
                {
                    val a = i1.displayName
                    val b = i2.displayName
                    a.unformattedComponentText.compareTo(b.unformattedComponentText)
                }
            }
        }

        private fun getWeight(i1: ItemStack): Int
        {
            return when (i1.item)
            {
                is BlockItem -> 1
                is ToolItem  -> 2
                else         -> 3
            }
        }
    }
}