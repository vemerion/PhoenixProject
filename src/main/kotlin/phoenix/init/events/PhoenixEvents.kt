package phoenix.init.events

import com.google.common.collect.ImmutableList
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.merchant.villager.VillagerProfession
import net.minecraft.entity.merchant.villager.VillagerTrades
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.MerchantOffer
import net.minecraft.loot.ItemLootEntry
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTables
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.DimensionType
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import phoenix.init.PhoenixBlocks
import phoenix.init.PhoenixItems
import phoenix.utils.LogManager
import phoenix.utils.StringUtils
import phoenix.utils.Tuple
import phoenix.world.GenSaveData
import java.util.*

object PhoenixEvents
{
    fun onPlay(event: PlayerEvent.PlayerRespawnEvent)
    {
        if(!event.player.world.isRemote)
        {
            val world = event.player.world as ServerWorld
            val player = event.player
            world.spawnParticle(ParticleTypes.PORTAL, player.posX, player.posY, player.posZ, 32, 0.1, 2.0, 0.1, 0.5)
        }
    }

    fun onLoad(event: WorldEvent.Load)
    {
        val world = event.world
        if(!event.world.isRemote && world is ServerWorld)
        {
            GenSaveData[world]
        }
    }

    fun tradesVillager(event: VillagerTradesEvent)
    {
        if(event.type == VillagerProfession.TOOLSMITH)
        {
            event.trades[1] = ImmutableList.of(VillagerTrades.ITrade { _: Entity, _: Random ->
                MerchantOffer(
                    ItemStack(Items.EMERALD, 8), ItemStack(
                        PhoenixItems.STEEL_AXE
                    ), 7, 1, 0.1f
                )
            })
        }
        else if(event.type == VillagerProfession.WEAPONSMITH)
        {
            event.trades[1] = ImmutableList.of(VillagerTrades.ITrade { _: Entity, _: Random ->
                MerchantOffer(
                    ItemStack(Items.EMERALD, 8), ItemStack(
                        PhoenixItems.STEEL_SWORD
                    ), 7, 1, 0.1f
                )
            })
        }
    }

    fun tradesWanderer(event: WandererTradesEvent)
    {
        event.rareTrades.add(VillagerTrades.ITrade { _: Entity, _: Random -> MerchantOffer(ItemStack(Items.EMERALD, 14), ItemStack(PhoenixBlocks.SETA), 7, 1, 0.1f) })
        event.rareTrades.add(VillagerTrades.ITrade { _: Entity, _: Random -> MerchantOffer(ItemStack(Items.EMERALD, 24), ItemStack(PhoenixItems.GOLDEN_SETA, 4), 7, 1, 0.1f) })
    }

    fun lootTables(event: LootTableLoadEvent)
    {
        if(event.name == LootTables.CHESTS_JUNGLE_TEMPLE)
        {
            event.table.addPool(LootPool.builder().addEntry(ItemLootEntry.builder(PhoenixItems.ZIRCONIUM_INGOT).weight(2)).build())
        }
    }

    private var tasks = ArrayList<Tuple<Int, Int, Runnable>>()

    fun deferredTasks(event: WorldTickEvent)
    {
        if (!event.world.isRemote)
        {
            if (tasks.isNotEmpty() && event.phase == TickEvent.Phase.END)
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

    fun cornGen(event: EntityJoinWorldEvent)
    {
        val world = event.world
        if (!world.isRemote && world.dimensionType === DimensionType.END_TYPE && world is ServerWorld && !GenSaveData[world].isCornGenned)
        {
            val manager = world.structureTemplateManager
            val template = manager.getTemplate(ResourceLocation("phoenix:corn/corn"))
            if (template != null)
            {
                GenSaveData[world].setCornGenned()
                template.func_237144_a_(world, BlockPos(1000, 100, 1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world, BlockPos(-1000, 100, 1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world, BlockPos(1000, 100, -1000), PlacementSettings(), world.rand)
                template.func_237144_a_(world, BlockPos(-1000, 100, -1000), PlacementSettings(), world.rand
                )
                LogManager.debug("Corn genned ^)")
            }
            else
            {
                LogManager.error("Phoenix Events Other", "Corn was not genned ^(. template is null... I think it is bad.")
            }
        }
    }

    fun onClientSetup(event: FMLClientSetupEvent)
    {
        val splashes = Minecraft.getInstance().splashes
        splashes.possibleSplashes.add(StringUtils.rainbowColor("God is an artist, since there are so many \n colors in the world"))
        splashes.possibleSplashes.add(TextFormatting.RED.toString() + "The essence of life is that it changes itself")
        splashes.possibleSplashes.add(TextFormatting.BLUE.toString() + "Bridge station is absent")
        splashes.possibleSplashes.add(TextFormatting.DARK_BLUE.toString() + "Third child is ann angel!!")
        splashes.possibleSplashes.add(TextFormatting.BLACK.toString() + "Project E.N.D.")
        splashes.possibleSplashes.add(TextFormatting.BLACK.toString() + "Нож в печень, FX вечен!")
    }
}