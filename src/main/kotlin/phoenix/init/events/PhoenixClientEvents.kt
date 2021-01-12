package phoenix.init.events

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import phoenix.client.render.OvenRenderer
import phoenix.client.render.entity.KnifeRenderer
import phoenix.client.render.entity.TalpaRenderer
import phoenix.init.*
import phoenix.init.PhoenixBlocks.BLOCKS
import phoenix.init.PhoenixEntities.KNIFE
import phoenix.init.PhoenixEntities.TALPA
import phoenix.network.NetworkHandler
import phoenix.other.PhoenixMusicTicker
import phoenix.utils.block.IColoredBlock
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object PhoenixClientEvents
{
    fun onClientSetup(event: FMLClientSetupEvent)
    {
        NetworkHandler.init()
        PhoenixRenderTypes.init()
        PhoenixContainers.registerScreens()
        RenderTypeLookup.setRenderLayer(PhoenixBlocks.OVEN, RenderType.getCutoutMipped())
        //RenderTypeLookup.setRenderLayer(PhoenixBlocks.PIPE, RenderType.getCutoutMipped())
        //RenderTypeLookup.setRenderLayer(PhoenixBlocks.TANK, RenderType.getCutoutMipped())
        RenderTypeLookup.setRenderLayer(PhoenixBlocks.ARMORED_GLASS, RenderType.getCutoutMipped())
        //RenderTypeLookup.setRenderLayer(PhoenixBlocks.TEXT_BLOCK.get(),    RenderType.getCutoutMipped())
        RenderingRegistry.registerEntityRenderingHandler(TALPA, ::TalpaRenderer)
        //RenderingRegistry.registerEntityRenderingHandler(CAUDA.get(), ::CaudaRenderer)
        RenderingRegistry.registerEntityRenderingHandler(KNIFE, ::KnifeRenderer)
        //ClientRegistry.bindTileEntityRenderer(PhoenixTiles.PIPE.get(), ::PipeRender)
        //ClientRegistry.bindTileEntityRenderer(PhoenixTiles.TANK.get(), ::TankRenderer)
        ClientRegistry.bindTileEntityRenderer(PhoenixTiles.OVEN, ::OvenRenderer)
        //ClientRegistry.bindTileEntityRenderer(PhoenixTiles.TEXT.get(), ::TextRenderer)

        // регистрация цветных блоков
        BLOCKS.getEntries().stream()
            .filter{ obj : ObjectHolderDelegate<*> -> obj.get() is Block }
            .map { block : ObjectHolderDelegate<out Block> -> block.get() }
            .forEach {
            block : Block ->
            if(block is IColoredBlock)
            {
                val blockColor = block.getBlockColor()
                val itemColor = block.getItemColor()
                if (blockColor != null) Minecraft.getInstance().blockColors.register(blockColor, block)
                if (itemColor  != null) Minecraft.getInstance().itemColors .register(itemColor,  block)
            }
        }

        Minecraft.getInstance().musicTicker = PhoenixMusicTicker(Minecraft.getInstance().musicTicker)
    }
}