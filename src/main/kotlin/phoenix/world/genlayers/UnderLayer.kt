package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId

object UnderLayer : ICastleTransformer
{
    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int): Int
    {
        return PhoenixBiomes.UNDER.getId()// if (context.random(5) == 0) PhoenixBiomes.UNDER.getId() else center
    }
}