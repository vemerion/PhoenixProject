package phoenix.init

import com.google.common.collect.ImmutableList
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraftforge.common.ToolType
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.blocks.UpdaterBlock
import phoenix.blocks.ash.OvenBlock
import phoenix.blocks.ash.PotteryBarrelBlock
import phoenix.blocks.redo.ArmoredGlassBlock
import phoenix.blocks.redo.ElectricBarrelBlock
import phoenix.blocks.redo.SetaBlock
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixBlocks
{
    @JvmStatic
    val BLOCKS = KDeferredRegister(ForgeRegistries.BLOCKS, Phoenix.MOD_ID)

    @JvmStatic
    val UPDATER           : Block by BLOCKS.register("updater") { UpdaterBlock }
    //val PIPE              by BLOCKS.register("pipe") { PipeBlock }
    //val TANK              by BLOCKS.register("tank") { TankBlock }
    val FERTILE_END_STONE : Block by BLOCKS.register("fertile_end_stone") { FertileEndStoneBlock }
    val ANTI_AIR          : Block by BLOCKS.register("anti_air")          { AntiAirBlock         }
    val POTTERY_BARREL    : Block by BLOCKS.register("pottery_barrel",    ::PotteryBarrelBlock)
    val ELECTRIC_BARREL   : Block by BLOCKS.register("electric_barrel",   ::ElectricBarrelBlock)
    val END_STONE_COLUMN  : Block by BLOCKS.register("end_stone_column")  { EndStoneColumnBlock }
    val OVEN              : Block by BLOCKS.register("oven",              ::OvenBlock           )
    val SETA              : Block by BLOCKS.register("seta") { SetaBlock }
    val ZIRCONIUM         : Block by BLOCKS.register("zirconium_ore") { ZirconiumOreBlock }
    //val TEXT_BLOCK       : RegistryObject<Block> = BLOCKS.register("block_with_text",   AnonimBlock.create(Material.ROCK))!!
    val ARMORED_GLASS     : Block by BLOCKS.register("armored_glass") { ArmoredGlassBlock }
}

object AntiAirBlock : AirBlock(Properties.create(Material.AIR).doesNotBlockMovement().noDrops().notSolid()), INonItem
object EndStoneColumnBlock : RotatedPillarBlock(Properties.create(Material.ROCK))
object FertileEndStoneBlock : Block(Properties.create(Material.ROCK)), ICustomGroup
{
    override fun getTab() = Phoenix.REDO
}
object ZirconiumOreBlock : OreBlock(Properties.create(Material.ROCK).hardnessAndResistance(3f).harvestTool(ToolType.PICKAXE))
{
    override fun getDrops(state: BlockState, builder: LootContext.Builder): ImmutableList<ItemStack> = ImmutableList.of(ItemStack(this))
}