package phoenix.init;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import phoenix.client.render.blocks.TankRender;
import phoenix.title.death.TileTank;
import phoenix.util.PCERFactory;

public class Client extends Common
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        EntityRegister.initModels();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        BlocksRegister.registerRender();
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TankRender());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        if(Configs.worldgen.grafics) {
            Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.END_ROD.getParticleID(), new PCERFactory());
        }
    }
}
