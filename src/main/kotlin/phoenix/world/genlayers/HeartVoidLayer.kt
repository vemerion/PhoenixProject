package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId

object HeartVoidLayer : ICastleTransformer
{
    var HEART_VOID_ID = PhoenixBiomes.HEART_VOID.getId()

    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int) = if (context.random(5) == 3 || isCurrect(north, west, east, south)) HEART_VOID_ID else center

    private fun isCurrect(a : Int, b : Int, c : Int, d : Int): Boolean
    {
        if(a == HEART_VOID_ID && b == HEART_VOID_ID && c == HEART_VOID_ID) return true
        if(b == HEART_VOID_ID && c == HEART_VOID_ID && d == HEART_VOID_ID) return true
        if(a == HEART_VOID_ID && c == HEART_VOID_ID && d == HEART_VOID_ID) return true
        if(a == HEART_VOID_ID && b == HEART_VOID_ID && d == HEART_VOID_ID) return true
        return false
    }
}