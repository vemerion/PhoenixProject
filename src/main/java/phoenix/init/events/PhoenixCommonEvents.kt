package phoenix.init.events

import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import phoenix.Phoenix
import phoenix.init.PhoenixBiomes
import phoenix.init.PhoenixBlocks.BLOCKS
import phoenix.init.PhoenixRecipes
import phoenix.network.NetworkHandler
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem

@EventBusSubscriber(modid = Phoenix.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
object PhoenixCommonEvents
{
    @SubscribeEvent
    @JvmStatic
    fun onRegisterItems(event: Register<Item>)
    {
        BLOCKS.entries.stream()
                .map { obj: RegistryObject<Block> -> obj.get() }
                .filter { block: Block -> block !is INonItem }
                .filter { block: Block -> block !is FlowingFluidBlock }
                .forEach { block: Block ->
                    val tab = if(block is ICustomGroup) block.tab else Phoenix.ASH
                    val prop = Item.Properties().group(tab)
                    val blockItem = BlockItem(block, prop)
                    blockItem.registryName = block.registryName
                    event.registry.register(blockItem)
                }
    }

    @SubscribeEvent
    @JvmStatic
    fun init(event: FMLCommonSetupEvent)
    {
        NetworkHandler.init()
        FMLJavaModLoadingContext.get().modEventBus.register(PhoenixCommonEvents::class.java)
        PhoenixRecipes.register()
        PhoenixBiomes.addBiomeTypes()
        /*
        for (biome in Registry.BIOME_KEY)
        {
            if (biome !== Biomes.END_BARRENS && biome !== Biomes.END_HIGHLANDS && biome !== Biomes.END_MIDLANDS && biome !== Biomes.THE_END && biome !== Biomes.SMALL_END_ISLANDS && biome !== UNDER.get() && biome !== HEARTVOID.get())
            {
                addZirconiumOre(biome)
            }
        }

         */
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