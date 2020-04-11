package ru.googletan.projectend.proxy;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.googletan.projectend.BlocksRegister;
import ru.googletan.projectend.TotalUpdatye.IStager;
import ru.googletan.projectend.TotalUpdatye.StageHandler;
import ru.googletan.projectend.TotalUpdatye.Storage;
import ru.googletan.projectend.world.BiomeRegistrar;
import ru.googletan.projectend.world.WorldProviderEndBiomes;

public class Common
{
    public void preInit(FMLPreInitializationEvent event)
    {
        BlocksRegister.register();
        CapabilityManager.INSTANCE.register(IStager.class, new Storage(), StageHandler.class);
    }
    public void init(FMLInitializationEvent event)
    {
        overrideEnd();
        BiomeRegistrar.registerBiomes();
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