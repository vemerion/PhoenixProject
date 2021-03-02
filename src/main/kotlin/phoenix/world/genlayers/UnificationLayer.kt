package phoenix.world.genlayers

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.layer.traits.IAreaTransformer2

object UnificationLayer : IAreaTransformer2
{
    override fun apply(random: INoiseRandom, phoenixBiomes: IArea, vanilaBiomes: IArea, x: Int, z: Int): Int
    {
        val phoenix = phoenixBiomes.getValue(x, z)
        return when(val vanila = vanilaBiomes.getValue(x, z))
        {
            SMALL_END_ISLANDS -> if(phoenix == UNDER_SMALL_ISLANDS && random.random(10) >= 5) phoenix else vanila
            END_HIGHLANDS     -> if((phoenix == HEART_VOID || phoenix == UNDER) && random.random(10) >= 5) phoenix else vanila
            else              -> vanila
        }
    }

    override fun getOffsetX(x: Int) = x
    override fun getOffsetZ(z: Int) = z
}