package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId

object UnderLayer : ICastleTransformer
{
    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int) = if(center == END_HIGHLANDS && context.random(5) == 0) UNDER else center
}