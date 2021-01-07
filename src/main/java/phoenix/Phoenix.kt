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

@Mod(Phoenix.MOD_ID)
object Phoenix
{
    const val MOD_ID = "phoenix"
    @JvmStatic
    val LOGGER = LogManager.getLogger()!!
    @JvmStatic
    val ASH: ItemGroup = PhoenixGroup("$MOD_ID.ash", Blocks.END_PORTAL_FRAME)
    @JvmStatic
    val REDO: ItemGroup = PhoenixGroup("$MOD_ID.redo", PhoenixBlocks.UPDATER)

    init
    {
        PhoenixLootTables.init()
    }
    init
    {
        //PhoenixEnchantments.register()
        //PhoenixBiomes.register()
        PhoenixBlocks.register()
        //PhoenixTiles.register()
        //PhoenixFeatures.register()
        //PhoenixEntities.register()
        PhoenixItems.register()
        //PhoenixContainers.register()
        //PhoenixRecipeSerializers.register()
        //val specPair = ForgeConfigSpec.Builder().configure(::Common)
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.right)
        //PhoenixConfiguration.COMMON_CONFIG = specPair.left
    }
}