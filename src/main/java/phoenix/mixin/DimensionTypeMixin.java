package phoenix.mixin;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import phoenix.world.EndBiomeProvider;

@Mixin(DimensionType.class)
public class DimensionTypeMixin
{
    @Inject(method = "getEndChunkGenerator", at = @At("HEAD"), cancellable = true)
    private static void getEndChunkGenerator(Registry<Biome> lookUpRegistryBiome, Registry<DimensionSettings> settingsRegistry, long seed, CallbackInfoReturnable<ChunkGenerator> cir) {
        cir.setReturnValue(new NoiseChunkGenerator(new EndBiomeProvider(lookUpRegistryBiome, seed), seed, () -> settingsRegistry.getOrThrow(DimensionSettings.field_242737_f)));
        cir.cancel();
    }
}
