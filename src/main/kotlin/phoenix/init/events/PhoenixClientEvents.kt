package phoenix.init.events

import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.util.text.TextFormatting.*
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import phoenix.Phoenix
import phoenix.client.render.EnderCrystalRenderer
import phoenix.client.render.OvenRenderer
import phoenix.client.render.PipeRender
import phoenix.client.render.TankRenderer
import phoenix.client.render.dragon.AshDragonRenderer
import phoenix.client.render.dragon.RedoDragonRenderer
import phoenix.client.render.entity.CaudaRenderer
import phoenix.client.render.entity.ExplosiveBallRenderer
import phoenix.client.render.entity.KnifeRenderer
import phoenix.client.render.entity.TalpaRenderer
import phoenix.init.PhxBlocks
import phoenix.init.PhxBlocks.BLOCKS
import phoenix.init.PhxContainers
import phoenix.init.PhxEntities.CAUDA
import phoenix.init.PhxEntities.DRAGON_ASH_STAGE
import phoenix.init.PhxEntities.DRAGON_REDO_STAGE
import phoenix.init.PhxEntities.ENDER_CRYSTAL
import phoenix.init.PhxEntities.KNIFE
import phoenix.init.PhxEntities.TALPA
import phoenix.init.PhxEntities.explosiveBall
import phoenix.init.PhxRenderTypes
import phoenix.init.PhxTiles
import phoenix.network.NetworkHandler
import phoenix.other.MusicTicker
import phoenix.utils.StringUtils
import phoenix.utils.block.IColoredBlock
import phoenix.utils.mc

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Phoenix.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
object PhoenixClientEvents
{
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent)
    {
        NetworkHandler.init()
        PhxRenderTypes.init()
        PhxContainers.registerScreens()
        RenderTypeLookup.setRenderLayer(PhxBlocks.oven, RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhxBlocks.pipe, RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhxBlocks.tank, RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhxBlocks.armoredGlass, RenderType.getCutoutMipped())
        //RenderTypeLookup.setRenderLayer(PhxBlocks.textBlock,    RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhxBlocks.wetLog, RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhxBlocks.setaJuice, RenderType.getCutoutMipped())
        RenderingRegistry.registerEntityRenderingHandler(TALPA, ::TalpaRenderer)
        RenderingRegistry.registerEntityRenderingHandler(CAUDA, ::CaudaRenderer)
        RenderingRegistry.registerEntityRenderingHandler(KNIFE, ::KnifeRenderer)
        RenderingRegistry.registerEntityRenderingHandler(DRAGON_ASH_STAGE, ::AshDragonRenderer)
        RenderingRegistry.registerEntityRenderingHandler(DRAGON_REDO_STAGE, ::RedoDragonRenderer)
        RenderingRegistry.registerEntityRenderingHandler(DRAGON_ASH_STAGE, ::AshDragonRenderer)
        RenderingRegistry.registerEntityRenderingHandler(explosiveBall, ::ExplosiveBallRenderer)
        RenderingRegistry.registerEntityRenderingHandler(ENDER_CRYSTAL, ::EnderCrystalRenderer)
        ClientRegistry.bindTileEntityRenderer(PhxTiles.PIPE, ::PipeRender)
        ClientRegistry.bindTileEntityRenderer(PhxTiles.TANK, ::TankRenderer)
        ClientRegistry.bindTileEntityRenderer(PhxTiles.OVEN, ::OvenRenderer)
        //ClientRegistry.bindTileEntityRenderer(PhxTiles.TEXT, ::TextRenderer)

        // регистрация цветных блоков
        for (block in BLOCKS.getEntries())
        {
            if (block is IColoredBlock)
            {
                if (block.getBlockColor() != null) mc.blockColors.register(block.getBlockColor()!!, block.get())
                if (block.getItemColor() != null) mc.itemColors.register(block.getItemColor()!!, block.get())
            }
        }

        val splashes = mc.splashes
        splashes.possibleSplashes.add(StringUtils.rainbowColor("God is an artist, since there are so many \n colors in the world")) //Reference to: Beautiful mind
        splashes.possibleSplashes.add("$RED The essence of life is that it changes itself") //Reference to: Evangelion-3.33 you can(not) redo
        splashes.possibleSplashes.add("$BLUE Bridge station is absent") //Reference to: Dovecote in a yellow glade
        splashes.possibleSplashes.add("$GRAY You can be wind... be forever.") //Reference to: Dovecote in a yellow glade
        splashes.possibleSplashes.add("$DARK_BLUE Third child is an angel!!") //Reference to: Neon Genesis Evangelion
        splashes.possibleSplashes.add("$GOLD Project E.N.D.") // Reference to: Phoenix project's old name
        splashes.possibleSplashes.add("$AQUA Still, the first enemy of human is itself.") // Reference to: Neon Genesis Evangelion
        splashes.possibleSplashes.add("$WHITE The hands of the clock cannot be turned back.$WHITE But it is in our power to move them forward!") // Reference to: Neon Genesis Evangelion
        splashes.possibleSplashes.add("$RED Where are the fixes, Lebowski?") // Reference to: The Big Lebowski

        mc.musicTicker = MusicTicker(mc.musicTicker)
    }
}