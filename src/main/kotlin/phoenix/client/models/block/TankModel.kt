package phoenix.client.models.block

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.client.renderer.model.RenderMaterial
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.fluid.Fluids
import net.minecraft.util.ResourceLocation
import net.minecraft.world.DimensionType
import phoenix.init.PhoenixRenderTypes
import phoenix.tile.redo.TankTile
import phoenix.utils.RenderUtils.refreshDrawing
import java.awt.Color

class TankModel(var tileTank: TankTile) : Model({ PhoenixRenderTypes.TANK })
{
    lateinit var TEXTURE_FLUID: ResourceLocation
    lateinit var MATERIAL_FLUID: RenderMaterial
    var fluid = ModelRenderer(this, 0, 0)
    var block = ModelRenderer(this, 0, 0)
    override fun render(matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int, packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float)
    {
        block.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        fluid.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
    }

    fun render(matrixStackIn: MatrixStack, buffer: IRenderTypeBuffer, packedLightIn: Int, packedOverlayIn: Int)
    {
        matrixStackIn.push()
        TEXTURE_FLUID = tileTank.input.fluid.fluid.attributes.stillTexture
        MATERIAL_FLUID = RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, TEXTURE_FLUID)
        val fluidBuilder = MATERIAL_FLUID.getBuffer(buffer, RenderType::getEntitySolid)
        if (tileTank.tank.fluid.fluid !== Fluids.WATER)
        {
            fluid.render(matrixStackIn, fluidBuilder, packedLightIn, packedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f)
        }
        else
        {
            if (tileTank.world!!.dimensionType === DimensionType.END_TYPE)
            {
                fluid.render(matrixStackIn, fluidBuilder, packedLightIn, packedOverlayIn, 80 / 256f, 43 / 256f, 226 / 256f, 0.7f)
            } else
            {
                val watercolor = Color(tileTank.world!!.getBiome(tileTank.pos).waterColor)
                fluid.render(matrixStackIn, fluidBuilder, packedLightIn, packedOverlayIn, watercolor.red.toFloat(), watercolor.green.toFloat(), 1.0f, 0.7f)
            }
        }
        val builder = buffer.getBuffer(PhoenixRenderTypes.TANK)
        block.render(matrixStackIn, builder, packedLightIn, packedOverlayIn)
        refreshDrawing(builder, PhoenixRenderTypes.TANK)
        matrixStackIn.pop()
    }

    companion object
    {
        val TEXTURE = RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, ResourceLocation("block/tank"))
    }

    init
    {
        fluid.setRotationPoint(0f, 0f, 0f)
        fluid.addBox(2f, 2f, 2f, 12f, (12 * tileTank.input.fluidAmount / tileTank.input.capacity).toFloat(), 12f)
        block.setRotationPoint(0f, 0f, 0f)
        block.addBox(0f, 0f, 0f, 16f, 16f, 16f)
    }
}