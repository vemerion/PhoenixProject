package phoenix.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoenix.utils.LogManager;

@Mixin(PlayerEntity.class)
public class EntityMixin
{
    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci)
    {
        LogManager.error(this, "you are so cute! :)");
    }
}
