package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer

object UnderSmallIslandsLayer : ICastleTransformer
{
    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int) =
        if(context.random(5) == 0 && center == SMALL_END_ISLANDS) UNDER_SMALL_ISLANDS else center
}