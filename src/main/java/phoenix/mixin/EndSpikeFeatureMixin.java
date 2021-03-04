package phoenix.mixin;

import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoenix.world.StageManager;

import java.util.Random;

@Mixin(EndSpikeFeature.class)
public class EndSpikeFeatureMixin
{
    @Inject(method = {"placeSpike"}, at = @At("HEAD"), cancellable = true)
    private void placeSpike(IServerWorld worldIn, Random rand, EndSpikeFeatureConfig config, EndSpikeFeature.EndSpike spike, CallbackInfo ci)
    {
        StageManager.INSTANCE.getStageEnum().createTower(worldIn, rand, config, spike);
        ci.cancel();
    }
}
