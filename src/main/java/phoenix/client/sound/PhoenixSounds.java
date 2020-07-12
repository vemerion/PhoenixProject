package phoenix.client.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import phoenix.Phoenix;

@Mod.EventBusSubscriber
public class PhoenixSounds
{
    //Это наш звук, `test_sound` это название звука указанного в sounds.json
    public static final SoundEvent trans[] = {reg("zero_one"), reg("one_two")};
    public static final SoundEvent step = reg("step");

    @SubscribeEvent
    public void regSound(RegistryEvent.Register<SoundEvent> e)
    {
        //Регистрация звука
        ForgeRegistries.SOUND_EVENTS.registerAll(trans);
        ForgeRegistries.SOUND_EVENTS.register(step);
    }

    //Упрощённая регистрация звука
    private static SoundEvent reg(String name)
    {
        ResourceLocation res = new ResourceLocation(Phoenix.MOD_ID, name);
        return new SoundEvent(res).setRegistryName(res);
    }
}
