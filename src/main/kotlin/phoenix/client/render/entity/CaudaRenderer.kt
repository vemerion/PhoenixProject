package phoenix.client.render.entity

//import phoenix.enity.CaudaEntity

/*
class CaudaRenderer(renderManager: EntityRendererManager) : MobRenderer<CaudaEntity, Cauda2Model<CaudaEntity>>(renderManager, Cauda2Model(), 1.5f)
{
    @Nonnull
    override fun getEntityTexture(entity: CaudaEntity): ResourceLocation
    {
        return if (entity.isChild) ResourceLocation(
            Phoenix.MOD_ID,
            "textures/entity/cauda/cauda_child.png"
        ) else ResourceLocation(Phoenix.MOD_ID, "textures/entity/cauda/cauda.png")
    }

    protected override fun applyRotations(
        entity: CaudaEntity,
        matrixStackIn: MatrixStack,
        ageInTicks: Float,
        rotationYaw: Float,
        partialTicks: Float
    )
    {
        super.applyRotations(entity, matrixStackIn, ageInTicks, rotationYaw, partialTicks)
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entity.rotationPitch))
    }

    protected override fun preRenderCallback(entity: CaudaEntity, matrixStackIn: MatrixStack, partialTickTime: Float)
    {
        val size = entity.caudaSize
        val scale = 1.0f + 0.25f * size.toFloat()
        matrixStackIn.scale(scale, scale, scale)
    }

    init
    {
        addLayer(CaudaEyesLayer(this))
    }
}

 */