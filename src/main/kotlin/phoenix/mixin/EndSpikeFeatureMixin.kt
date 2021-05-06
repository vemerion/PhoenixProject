package phoenix.mixin

import net.minecraft.world.IServerWorld
import net.minecraft.world.gen.feature.EndSpikeFeature
import net.minecraft.world.gen.feature.EndSpikeFeature.EndSpike
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import phoenix.world.StageManager.stageEnum
import java.util.*

@Mixin(EndSpikeFeature::class)
class EndSpikeFeatureMixin
{
    @Inject(method = ["placeSpike"], at = [At("HEAD")], cancellable = true)
    private fun placeSpike
        (
            worldIn: IServerWorld,
            rand: Random,
            config: EndSpikeFeatureConfig,
            spike: EndSpike,
            ci: CallbackInfo
        )
    {
        stageEnum.createTower(worldIn, rand, config, spike)
        ci.cancel()
    }
}