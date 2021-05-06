package phoenix.init

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.EnumValue
import net.minecraftforge.common.ForgeConfigSpec.IntValue


object PhoenixConfiguration
{
    lateinit var COMMON_CONFIG: Common

    val biomeSize: Int
        get() = COMMON_CONFIG.BIOME_SIZE.get()
    val isDebugMode: Boolean
        get() = COMMON_CONFIG.debug.get()
    val gameMode : GameMode
        get() = COMMON_CONFIG.gameMode.get()

    class Common(builder: ForgeConfigSpec.Builder)
    {
        var gameMode: EnumValue<GameMode>
        var BIOME_SIZE: IntValue
        var debug : ForgeConfigSpec.BooleanValue
        init
        {
            builder.push("Game Settings")
            gameMode = builder.worldRestart().comment("If \"Liahim\" game will be hard and trolling like Misty World or higher" +
                    ", but if \"hohserg\" it will be easier and more simple.")
                    .defineEnum("Game mode", GameMode.normal)
            BIOME_SIZE = builder.worldRestart().defineInRange("Biome size", 3, 1, 15)
            debug = builder.worldRestart().define("is debug mode", true)
            builder.pop()
        }
    }

    enum class GameMode(var maxKnifeUsages: Int)
    {
        normal(60), Liahim(16), hohserg(80);
    }
}