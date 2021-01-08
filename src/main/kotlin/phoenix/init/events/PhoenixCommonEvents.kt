package phoenix.init.events

import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix.ASH
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixBlocks.BLOCKS
import phoenix.init.PhoenixRecipes
import phoenix.network.NetworkHandler
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.*

object PhoenixCommonEvents
{
    fun onRegisterItems(event: RegistryEvent.Register<Item>)
    {
        if(event.registry == ForgeRegistries.ITEMS)
        {
            val blocks: ArrayList<Block> = ArrayList()

            for (i: ObjectHolderDelegate<out Block> in BLOCKS.getEntries())
            {
                blocks.add(i.get())
            }

            for (block: Block in blocks)
            {
                if (block !is INonItem && block !is FlowingFluidBlock)
                {
                    val tab = if (block is ICustomGroup) block.getTab() else ASH
                    val blockItem = BlockItem(block, Item.Properties().group(tab))
                    blockItem.registryName = block.registryName
                    event.registry.register(blockItem)
                }
            }
        }
    }

    fun init(event: FMLCommonSetupEvent)
    {
        NetworkHandler.init()
        PhoenixRecipes.register()
        PhoenixBiomes.addBiomeTypes()
    }
    /*
    private fun addZirconiumOre(biome: Biome)
    {
        biome.addFeature(
            GenerationStage.Decoration.UNDERGROUND_ORES,
            Feature.ORE.withConfiguration(
                OreFeatureConfig(
                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ZIRCONIUM.get().defaultState,
                    4
                )
            )
                .withPlacement(Placement.COUNT_RANGE.configure(CountRangeConfig(20, 0, 0, 64)))
        )
    }
     */
}