package phoenix.client.render

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import phoenix.client.models.block.PipeModel
import phoenix.init.PhoenixRenderTypes
import phoenix.tile.redo.PipeTile
import phoenix.utils.RenderUtils


class PipeRender(rendererDispatcherIn: TileEntityRendererDispatcher) : TileEntityRenderer<PipeTile>(rendererDispatcherIn)
{
    override fun render(
        tile: PipeTile,
        partialTicks: Float,
        matrix: MatrixStack,
        buffer: IRenderTypeBuffer,
        light: Int,
        overlay: Int
    )
    {
        val model = PipeModel(tile.blockState)
        matrix.push()
        val builder = buffer.getBuffer(PhoenixRenderTypes.PIPE)
        model.render(matrix, builder, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f)
        RenderUtils.refreshDrawing(builder, PhoenixRenderTypes.PIPE)
        matrix.pop()
    }
}