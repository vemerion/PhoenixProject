package phoenix.init;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
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
        for (Block block : blocks)
        {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TankRender());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        if(Configs.worldgen.graphics) {
            Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.END_ROD.getParticleID(), new PCERFactory());
        }
    }
}
