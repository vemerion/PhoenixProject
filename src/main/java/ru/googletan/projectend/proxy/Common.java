package ru.googletan.projectend.proxy;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.googletan.projectend.BlocksRegister;
import ru.googletan.projectend.world.BiomeRegistrar;
import ru.googletan.projectend.world.WorldProviderEndBiomes;
import ru.googletan.projectend.world.capablity.IStager;
import ru.googletan.projectend.world.capablity.StageHandler;
import ru.googletan.projectend.world.capablity.StageStorage;
import ru.googletan.projectend.world.structures.Unit01.WorldGenCorn;

public class Common
{
    public void preInit(FMLPreInitializationEvent event)
    {
        BlocksRegister.register();
        CapabilityManager.INSTANCE.register(IStager.class, new StageStorage(), StageHandler.class);
    }
    public void init(FMLInitializationEvent event)
    {
        overrideEnd();
        BiomeRegistrar.registerBiomes();
        GameRegistry.registerWorldGenerator(new WorldGenCorn(), 5);
    }
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    public void overrideEnd()
    {
        DimensionManager.unregisterDimension(1);
        DimensionType endBiomes = DimensionType.register("End", "_end", 1, WorldProviderEndBiomes.class, false);
        DimensionManager.registerDimension(1, endBiomes);
    }
}