package phoenix.client.render.entity

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation
import phoenix.Phoenix
import phoenix.client.models.entity.TalpaModel
import phoenix.enity.TalpaEntity

class TalpaRenderer(renderManager: EntityRendererManager) : MobRenderer<TalpaEntity, TalpaModel>(renderManager, TalpaModel(), 0.5f)
{
    override fun getEntityTexture(entity: TalpaEntity): ResourceLocation
    {
        return if (entity.isChild) ResourceLocation(
            Phoenix.MOD_ID,
            "textures/entity/talpa_child.png"
        ) else ResourceLocation(Phoenix.MOD_ID, "textures/entity/talpa.png")
    }
}