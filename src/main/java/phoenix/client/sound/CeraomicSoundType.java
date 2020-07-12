package phoenix.client.sound;

import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

public class CeraomicSoundType extends SoundType
{
    public static final SoundType TEST = new SoundType(1.0F, 1.0F,
            PhoenixSounds.step,
            PhoenixSounds.step,
            PhoenixSounds.step,
            PhoenixSounds.step,
            PhoenixSounds.step);

    public CeraomicSoundType(float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound)
    {
        super(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }
}
