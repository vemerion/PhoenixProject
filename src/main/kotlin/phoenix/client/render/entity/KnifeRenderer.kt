package phoenix.client.render.entity

import com.mojang.blaze3d.matrix.MatrixStack
import mcp.MethodsReturnNonnullByDefault
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f
import phoenix.enity.KnifeEntity
import phoenix.init.PhoenixItems.ZIRCONIUM_KNIFE
import javax.annotation.ParametersAreNonnullByDefault


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
class KnifeRenderer(renderManagerIn: EntityRendererManager) : EntityRenderer<KnifeEntity>(renderManagerIn)
{
    private val itemRenderer: ItemRenderer = Minecraft.getInstance().itemRenderer
    private val scale = 1f
    override fun render(
        entityIn: KnifeEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStackIn: MatrixStack,
        bufferIn: IRenderTypeBuffer,
        packedLightIn: Int
    )
    {
        matrixStackIn.push()
        matrixStackIn.scale(scale, scale, scale)
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0f))
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0f))
        itemRenderer.renderItem(
            ItemStack(ZIRCONIUM_KNIFE),
            ItemCameraTransforms.TransformType.GROUND,
            packedLightIn,
            OverlayTexture.NO_OVERLAY,
            matrixStackIn,
            bufferIn
        )
        matrixStackIn.pop()
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn)
    }

    override fun getEntityTexture(entity: KnifeEntity): ResourceLocation
    {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE
    }

}
