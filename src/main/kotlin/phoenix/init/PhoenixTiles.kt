package phoenix.init

import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix
import phoenix.tile.ash.OvenTile
import phoenix.tile.ash.PotteryBarrelTile
import phoenix.tile.redo.ElectricBarrelTile
import phoenix.tile.redo.PipeTile
import phoenix.tile.redo.TankTile
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object PhoenixTiles
{
    val TILE_ENTITIES = KDeferredRegister(ForgeRegistries.TILE_ENTITIES, Phoenix.MOD_ID)

    val POTTERY_BARREL  by TILE_ENTITIES.register("pottery_barrel") { TileEntityType.Builder.create({ PotteryBarrelTile() }, PhoenixBlocks.POTTERY_BARREL).build(null) }
    val OVEN            by TILE_ENTITIES.register("oven")           { TileEntityType.Builder.create({ OvenTile() }, PhoenixBlocks.OVEN).build(null) }
    val ELECTRIC_BARREL by TILE_ENTITIES.register("_barrel") { TileEntityType.Builder.create({ ElectricBarrelTile() }, PhoenixBlocks.POTTERY_BARREL).build(null) }
}