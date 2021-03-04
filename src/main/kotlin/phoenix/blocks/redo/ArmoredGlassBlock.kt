package phoenix.blocks.redo

import net.minecraft.block.AbstractGlassBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import net.minecraftforge.common.ToolType
import phoenix.Phoenix
import phoenix.init.PhoenixBlocks
import phoenix.utils.block.ICustomGroup
import java.util.*

object ArmoredGlassBlock : AbstractGlassBlock(
    Properties.create(Material.GLASS).doesNotBlockMovement().hardnessAndResistance(20.0f).harvestLevel(3).harvestTool(
        ToolType.PICKAXE).sound(SoundType.GLASS)), ICustomGroup
{
    override fun getTab() = Phoenix.REDO

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>
    {
        val res = ArrayList<ItemStack>()
        res.add(ItemStack(PhoenixBlocks.ARMORED_GLASS));
        return res
    }

    override fun isIn(tagIn: ITag<Block>): Boolean
    {
        return super.isIn(tagIn) || tagIn == BlockTags.DRAGON_IMMUNE
    }
}