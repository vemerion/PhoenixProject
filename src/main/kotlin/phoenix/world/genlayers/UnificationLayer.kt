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
        return if (vanila == THE_END || vanila == SMALL_END_ISLANDS || vanila == END_BARRENS || phoenix != UNDER)// && phoenix != HEART_VOID)
        {
            vanila
        } else
        {
            phoenix
        }
    }

    override fun getOffsetX(x: Int) = x
    override fun getOffsetZ(z: Int) = z

    private val END_BARRENS: Int = Biomes.END_BARRENS.getId()
    private val THE_END: Int = Biomes.THE_END.getId()
    private val SMALL_END_ISLANDS: Int = Biomes.SMALL_END_ISLANDS.getId()
    private val UNDER: Int = PhoenixBiomes.UNDER.getId()
    //private val HEART_VOID: Int = Registry.BIOME.getId(PhoenixBiomes.HEARTVOID.get())
}