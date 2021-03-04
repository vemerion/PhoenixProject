package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer

object UnderLayer : ICastleTransformer
{
    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int) = if(context.random(5) == 0 && center == END_HIGHLANDS) UNDER else center
}