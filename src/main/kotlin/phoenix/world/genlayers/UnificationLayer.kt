package phoenix.world.genlayers

import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.layer.traits.IAreaTransformer2
import phoenix.init.PhoenixBiomes
import phoenix.utils.getId

object UnificationLayer : IAreaTransformer2
{
    override fun apply(random: INoiseRandom, area1: IArea, area2: IArea, x: Int, z: Int): Int
    {
        val phoenix = area1.getValue(x, z)
        val vanila = area2.getValue(x, z)
        return if ((vanila == END_MIDLANDS || vanila == END_HIGHLANDS) && (phoenix == UNDER || phoenix == HEART_VOID)) phoenix else vanila
    }

    override fun getOffsetX(x: Int) = x
    override fun getOffsetZ(z: Int) = z

    private val END_HIGHLANDS: Int = Biomes.END_HIGHLANDS.getId()
    private val END_MIDLANDS : Int = Biomes.END_MIDLANDS.getId()
    private val UNDER        : Int = PhoenixBiomes.UNDER.getId()
    private val HEART_VOID   : Int = PhoenixBiomes.HEART_VOID.getId()
}