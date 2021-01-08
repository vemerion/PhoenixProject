package phoenix.init.events

import com.google.common.collect.ImmutableList
import net.minecraft.entity.Entity
import net.minecraft.entity.merchant.villager.VillagerProfession
import net.minecraft.entity.merchant.villager.VillagerTrades
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.MerchantOffer
import net.minecraft.loot.ItemLootEntry
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTables
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.DimensionType
import net.minecraft.world.IServerWorld
import net.minecraft.world.World
import net.minecraft.world.gen.feature.template.PlacementSettings
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.WorldTickEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.village.VillagerTradesEvent
import net.minecraftforge.event.village.WandererTradesEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.Level
import phoenix.Phoenix
import phoenix.init.PhoenixBlocks
import phoenix.init.PhoenixItems
import phoenix.network.NetworkHandler
import phoenix.network.NetworkHandler.sendTo
import phoenix.network.SyncBookPacket
import phoenix.network.SyncStagePacket
import phoenix.utils.IChapterReader
import phoenix.utils.LogManager
import phoenix.utils.LogManager.error
import phoenix.utils.Tuple
import phoenix.world.GenSaveData
import phoenix.world.StageManager
import java.util.*

@Mod.EventBusSubscriber
object PhoenixEvents
{
    @JvmStatic
    @SubscribeEvent
    fun onPlay(event: PlayerEvent.PlayerRespawnEvent)
    {
        if(!event.player.world.isRemote)
        {
            val world = event.player.world as ServerWorld
            val player = event.player
            LogManager.log(this, "Particles!!!")
            world.spawnParticle(ParticleTypes.PORTAL, player.posX, player.posY, player.posZ, 32, 0.1, 2.0, 0.1, 0.5)
        }
    }
    /*
    @JvmStatic
    @SubscribeEvent
    fun onSave(event: WorldEvent.Save)
    {
        val world = event.world
        if(!event.world.isRemote && world is ServerWorld)
        {
            LogManager.log(this, "Phoenix is starting saving")
            val nbt = event.world.worldInfo.getDimensionData(DimensionType.THE_END)
            StageManager.write(nbt)
            LogManager.log(this, "${StageManager.getStage()} ${StageManager.getPart()}")
            event.world.worldInfo.setDimensionData(DimensionType.THE_END, nbt)
            LogManager.log(this, "Phoenix has ended saving")
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onLoad(event: WorldEvent.Load)
    {
        if(!event.world.isRemote)
        {
            LogManager.log(this, "Phoenix is starting loading")
            val nbt = event.world.worldInfo.getDimensionData(DimensionType.THE_END)
            StageManager.read(nbt)
            LogManager.log(this, "${StageManager.getStage()} ${StageManager.getPart()}")
            NetworkHandler.sendToAll(SyncStagePacket(StageManager.getStage(), StageManager.getPart()))
            LogManager.log(this, "Phoenix has ended loading")
        }
    }

     */

    @SubscribeEvent
    @JvmStatic
    fun tradesVillager(event: VillagerTradesEvent)
    {
        if(event.type == VillagerProfession.TOOLSMITH)
        {
            event.trades.put(1,
                ImmutableList.of(VillagerTrades.ITrade { _: Entity, _: Random ->
                    MerchantOffer(
                        ItemStack(Items.EMERALD, 8), ItemStack(
                            PhoenixItems.STEEL_AXE
                        ), 7, 1, 0.1f
                    )
                }))
        }
        else if(event.type == VillagerProfession.WEAPONSMITH)
        {
            event.trades.put(1,
                ImmutableList.of(VillagerTrades.ITrade { _: Entity, _: Random ->
                    MerchantOffer(
                        ItemStack(Items.EMERALD, 8), ItemStack(
                            PhoenixItems.STEEL_SWORD
                        ), 7, 1, 0.1f
                    )
                }))
        }
    }

    @SubscribeEvent
    @JvmStatic
    fun tradesWanderer(event: WandererTradesEvent)
    {
        event.rareTrades.add(VillagerTrades.ITrade { _: Entity, _: Random -> MerchantOffer(ItemStack(Items.EMERALD, 14), ItemStack(PhoenixBlocks.SETA), 7, 1, 0.1f) })
        event.rareTrades.add(VillagerTrades.ITrade { _: Entity, _: Random -> MerchantOffer(ItemStack(Items.EMERALD, 24), ItemStack(PhoenixItems.GOLDEN_SETA, 4), 7, 1, 0.1f) })
    }

    @SubscribeEvent
    @JvmStatic
    fun capa(event: LootTableLoadEvent)
    {
        if(event.name == LootTables.CHESTS_JUNGLE_TEMPLE)
        {
            event.table.addPool(LootPool.builder().addEntry(ItemLootEntry.builder(PhoenixItems.ZIRCONIUM_INGOT).weight(2)).build())
        }
    }

    var tasks = ArrayList<Tuple<Int, Int, Runnable>>()

    @JvmStatic
    @SubscribeEvent
    fun deferredTasks(event: WorldTickEvent)
    {
        if (!event.world.isRemote)
        {
            if (!tasks.isEmpty()) if (event.phase == TickEvent.Phase.END)
            {
                var i = 0
                while (i < tasks.size)
                {
                    val current = tasks[i]
                    current.first++
                    if (current.first >= current.second)
                    {
                        current.third.run()
                        tasks.removeAt(i)
                        i--
                    }
                    ++i
                }
            }
        }
    }

    fun addTask(time: Int, r: Runnable)
    {
        tasks.add(Tuple(0, time, r))
    }

    @SubscribeEvent
    fun cornGen(event: EntityJoinWorldEvent)
    {
        val world = event.world
        if (!world.isRemote && world.dimensionType === DimensionType.END_TYPE && !GenSaveData.get(world as ServerWorld).isCornGenned)
        {
            val manager = world.structureTemplateManager
            val template = manager.getTemplate(ResourceLocation("phoenix:corn/corn"))
            if (template != null)
            {
                GenSaveData.get(world).setCornGenned()
                template.func_237144_a_(world as IServerWorld, BlockPos(1000, 100, 1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world as IServerWorld, BlockPos(-1000, 100, 1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world as IServerWorld, BlockPos(1000, 100, -1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world as IServerWorld, BlockPos(-1000, 100, -1000), PlacementSettings(), world.rand
                )
                Phoenix.LOGGER.log(Level.DEBUG, "Corn genned ^)")
            } else
            {
                error("Phoenix Events Other", "Corn was not genned ^(. template is null... I think it is bad.")
            }
        }
    }
    /*
    @SubscribeEvent
    public static void onOpenGui(GuiOpenEvent event)
    {
        if(event.getGui() instanceof MainMenuScreen)
        {
            Splashes splashes = Minecraft.getInstance().splashes;
            splashes.possibleSplashes.add(StringUtils.rainbowColor("God is an artist, since there are so many \n colors in the world"));
            splashes.possibleSplashes.add(TextFormatting.RED + "The essence of life is that it changes itself");
            splashes.possibleSplashes.add(TextFormatting.BLUE + "Bridge station is absent");
            splashes.possibleSplashes.add(TextFormatting.DARK_BLUE + "Third child is ann angel!!");
            splashes.possibleSplashes.add(TextFormatting.BLACK + "Project E.N.D.");
            splashes.possibleSplashes.add(TextFormatting.BLACK + "Нож в печень, FX вечен!");
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onSave(event: PlayerEvent.PlayerChangedDimensionEvent)
    {
        if(!event.player.world.isRemote)
        {
            LogManager.log(this, "Phoenix is starting saving")
            val nbt = event.player.world.worldInfo.getDimensionData(DimensionType.THE_END)
            StageManager.write(nbt)
            LogManager.log(this, "${StageManager.getStage()} ${StageManager.getPart()}")
            event.player.world.worldInfo.setDimensionData(DimensionType.THE_END, nbt)
            LogManager.log(this, "Phoenix has ended saving")
        }
    }

     */
}