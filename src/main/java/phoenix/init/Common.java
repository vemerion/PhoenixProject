package phoenix.init;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import phoenix.world.BiomeRegister;
import phoenix.world.WorldProviderEndBiomes;
import phoenix.world.capablity.IStager;
import phoenix.world.capablity.StageHandler;
import phoenix.world.capablity.StageStorage;
import phoenix.world.structures.Unit01.WorldGenCorn;
import phoenix.world.structures.Unit02.WorldGenHome;

public class Common
{
    public void preInit(FMLPreInitializationEvent event)
    {
        FluidRegister.register();
        CapabilityManager.INSTANCE.register(IStager.class, new StageStorage(), StageHandler.class);//reg capablity
    }
    public void init(FMLInitializationEvent event)
    {
        overrideEnd();
        BiomeRegister.registerBiomes();
       // MapGenStructureIO.registerStructure(MapGenCorn.Start.class, "Corn");
        GameRegistry.registerWorldGenerator(new WorldGenCorn(), 5);
        GameRegistry.registerWorldGenerator(new WorldGenHome(), 3);
    }
    public void postInit(FMLPostInitializationEvent event) {  }

    public void overrideEnd()
    {
        DimensionManager.unregisterDimension(1);
        DimensionType endBiomes = DimensionType.register("End", "_end", 1, WorldProviderEndBiomes.class, false);
        DimensionManager.registerDimension(1, endBiomes);
    }
}