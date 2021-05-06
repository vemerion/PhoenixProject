package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId

object HeartVoidLayer : ICastleTransformer
{
    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int)
                    = if (isCorrect(center) && (context.random(5) == 3 || isCorrect(north, west, east, south))) HEART_VOID else center

    private fun isCorrect(a : Int, b : Int, c : Int, d : Int): Boolean
    {
        return  a == HEART_VOID && b == HEART_VOID && c == HEART_VOID ||
                d == HEART_VOID && b == HEART_VOID && c == HEART_VOID ||
                a == HEART_VOID && d == HEART_VOID && c == HEART_VOID ||
                a == HEART_VOID && b == HEART_VOID && d == HEART_VOID
    }

    private fun isCorrect(a : Int): Boolean = a == END_HIGHLANDS || a == END_MIDLANDS
}