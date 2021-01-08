package phoenix

import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import phoenix.init.*
import phoenix.init.PhoenixConfiguration.Common
import phoenix.init.events.PhoenixClientEvents
import phoenix.init.events.PhoenixCommonEvents
import phoenix.init.events.PhoenixEvents
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Phoenix.MOD_ID)
object Phoenix
{
    const val MOD_ID = "phoenix"
    val LOGGER = LogManager.getLogger()!!
    val ASH: ItemGroup = PhoenixGroup("$MOD_ID.ash", Blocks.END_PORTAL_FRAME)
    val REDO: ItemGroup = PhoenixGroup("$MOD_ID.redo") { PhoenixBlocks.SETA }

    init
    {
        //PhoenixLootTables.init()
        //PhoenixBiomes.register()
        //PhoenixFeatures.register()
        //PhoenixRecipeSerializers.register()
        val specPair = ForgeConfigSpec.Builder().configure(::Common)

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.right)
        PhoenixConfiguration.COMMON_CONFIG = specPair.left
        PhoenixBlocks.BLOCKS.register(MOD_BUS)
        PhoenixContainers.CONTAINERS.register(MOD_BUS)
        PhoenixItems.ITEMS.register(MOD_BUS)
        PhoenixEntities.ENTITIES.register(MOD_BUS)
        PhoenixTiles.TILE_ENTITIES.register(MOD_BUS)
        PhoenixEnchantments.ENCHANTMENTS.register(MOD_BUS)
        PhoenixRecipeSerializers.RS.register(MOD_BUS)
        MOD_BUS.addListener(PhoenixCommonEvents::onRegisterItems)
        MOD_BUS.addListener(PhoenixCommonEvents::init)
        MOD_BUS.addListener(PhoenixClientEvents::onClientSetup)
        MOD_BUS.addListener(PhoenixEvents::tradesVillager)
        MOD_BUS.addListener(PhoenixEvents::tradesWanderer)
        MOD_BUS.addListener(PhoenixEvents::capa)
        MOD_BUS.addListener(PhoenixEvents::deferredTasks)
        MOD_BUS.addListener(PhoenixEvents::onPlay)
        MOD_BUS.addListener(PhoenixEvents::cornGen)
    }
}