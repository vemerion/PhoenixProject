package phoenix.init

import com.google.common.collect.ImmutableList
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraftforge.common.ToolType
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.blocks.UpdaterBlock
import phoenix.blocks.ash.*
import phoenix.blocks.redo.*
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

object PhoenixBlocks
{
    @JvmStatic
    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Phoenix.MOD_ID)!!

    @JvmStatic
    val UPDATER          : RegistryObject<Block> = BLOCKS.register("updater",           ::UpdaterBlock)!!
    val PIPE             : RegistryObject<Block> = BLOCKS.register("pipe",              ::PipeBlock)!!
    val TANK             : RegistryObject<Block> = BLOCKS.register("tank")              { TankBlock            }!!
    val FERTILE_END_STONE: RegistryObject<Block> = BLOCKS.register("fertile_end_stone") { FertileEndStoneBlock }!!
    val ANTI_AIR         : RegistryObject<Block> = BLOCKS.register("anti_air")          { AntiAirBlock         }!!
    val POTTERY_BARREL   : RegistryObject<Block> = BLOCKS.register("pottery_barrel",    ::PotteryBarrelBlock)!!
    val ELECTRIC_BARREL  : RegistryObject<Block> = BLOCKS.register("electric_barrel",   ::ElectricBarrelBlock)!!
    val END_STONE_COLUMN : RegistryObject<Block> = BLOCKS.register("end_stone_column")  { EndStoneColumnBlock }!!
    val OVEN             : RegistryObject<Block> = BLOCKS.register("oven",              ::OvenBlock           )!!
    val SETA             : RegistryObject<Block> = BLOCKS.register("seta")              { SetaBlock }!!
    val ZIRCONIUM        : RegistryObject<Block> = BLOCKS.register("zirconium_ore")     { ZirconiumOreBlock }!!
    //val TEXT_BLOCK       : RegistryObject<Block> = BLOCKS.register("block_with_text",   AnonimBlock.create(Material.ROCK))!!
    val ARMORED_GLASS    : RegistryObject<Block> = BLOCKS.register("armored_glass")     { ArmoredGlassBlock }!!

    @JvmStatic
    fun register()
    {
        BLOCKS.register(thedarkcolour.kotlinforforge.forge.MOD_CONTEXT.getKEventBus())
    }
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