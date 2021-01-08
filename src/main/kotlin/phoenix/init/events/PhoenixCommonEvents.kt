package phoenix.init.events

import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biomes
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import phoenix.Phoenix
import phoenix.Phoenix.ASH
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixBlocks.BLOCKS
import phoenix.init.PhoenixRecipes
import phoenix.network.NetworkHandler
import phoenix.utils.LogManager
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.*

@EventBusSubscriber(modid = Phoenix.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
object PhoenixCommonEvents
{
    @SubscribeEvent
    fun onRegisterItems(event: Register<Item>)
    {
        val blocks : ArrayList<Block> = ArrayList()

        for (i in BLOCKS.getEntries())
        {
            try
            {
                val  block = i.get()
                if(block is Block)
                    blocks.add(block)
            }
            catch (e : Exception)
            {
            }
        }

        for (block : Block in blocks)
        {
            if (block !is INonItem && block !is FlowingFluidBlock)
            {
                val blockItem = Item(Item.Properties())
                blockItem.registryName = ResourceLocation(block.registryName.toString() + "_item")

                try
                {
                    //event.registry.register(blockItem)
                }
                catch (e : Exception)
                {
                    LogManager.error(this, block.toString() + " " + block::class.toString())
                }
            }
        }
    }

    @SubscribeEvent
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