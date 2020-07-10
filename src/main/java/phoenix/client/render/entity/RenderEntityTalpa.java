package phoenix.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import phoenix.Phoenix;
import phoenix.client.models.entity.ModelTalpa;
import phoenix.entity.rebirth.EntityTalpa;

import javax.annotation.Nullable;

public class RenderEntityTalpa extends RenderLiving<EntityTalpa>
{
    /*
    Конструктор рендера,
    теперь о super:
    1 параметр - наш RenderManager,
    2 параметр - наша модель,
    3 параметр - размер тени(стандартно 0.5F)
    */
    public RenderEntityTalpa(RenderManager manager)
    {
        super(manager, new ModelTalpa(), 1F);
    }
    public static Factory FACTORY = new Factory();


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTalpa entity)
    {
       return new ResourceLocation(Phoenix.MOD_ID, "textures/entity/talpa.png");
    }


    /*--------->НАШ РЕНДЕР ФЭКТОРИ <---------*/
    public static class Factory implements IRenderFactory<EntityTalpa>
    {
        @Override
        public Render<? super EntityTalpa> createRenderFor(RenderManager manager) {
            /*И наконец-то из всего этого создаём рендер*/
            return new RenderEntityTalpa(manager);
        }
    }
}
