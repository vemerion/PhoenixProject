package phoenix.client.render

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.item.ItemStack
import net.minecraft.util.Direction
import net.minecraft.util.math.vector.Quaternion
import net.minecraft.util.math.vector.Vector3f
import phoenix.init.PhoenixItems
import phoenix.tile.ash.OvenTile

class OvenRenderer(rendererDispatcherIn: TileEntityRendererDispatcher) : TileEntityRenderer<OvenTile>(rendererDispatcherIn)
{
    override fun render(te: OvenTile, partialTicks: Float, matrixStackIn: MatrixStack, bufferIn: IRenderTypeBuffer, combinedLightIn: Int, combinedOverlayIn: Int)
    {
        val direction: Direction = te.blockState[AbstractFurnaceBlock.FACING]
        matrixStackIn.translate(0.0, 1.0, 0.0)
        for (i in 0..te.inventory.size - 1)
        {
            val stack: ItemStack = te.inventory[i]
            if (stack != ItemStack.EMPTY)
            {
                matrixStackIn.push()
                matrixStackIn.translate(0.5, 0.44921875, 0.5)
                val direction1 = Direction.byHorizontalIndex((i + direction.horizontalIndex) % 4)
                val dirAngle = -direction1.horizontalAngle
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(dirAngle))
                matrixStackIn.translate(-0.15, -0.3125, 0.15)
                if (stack.item.containerItem == PhoenixItems.CRUCIBLE)
                {
                    matrixStackIn.scale(0.7f, 0.7f, 0.7f)
                }
                else
                {
                    matrixStackIn.translate(0.0, -0.1, 0.0)
                    matrixStackIn.scale(0.3f, 0.3f, 0.3f)
                    matrixStackIn.rotate(Quaternion(90f, 0f, 90f, true))
                }
                Minecraft.getInstance().itemRenderer.renderItem(
                    stack,
                    ItemCameraTransforms.TransformType.FIXED,
                    combinedLightIn,
                    combinedOverlayIn,
                    matrixStackIn,
                    bufferIn
                )
                matrixStackIn.pop()
            }
        }
    }

}