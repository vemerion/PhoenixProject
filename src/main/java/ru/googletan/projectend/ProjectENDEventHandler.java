package ru.googletan.projectend;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.googletan.projectend.TotalUpdatye.Provider;

@Mod.EventBusSubscriber
public class ProjectENDEventHandler
{
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<World> event)
    {
        event.addCapability(new ResourceLocation(Projectend.MOD_ID, Projectend.MOD_NAME), new Provider());
    }
}
