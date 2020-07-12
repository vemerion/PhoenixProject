package phoenix.world.genlayers;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import phoenix.init.Configs;

public class GenLayerReduceFrequency extends GenLayer
{
    public GenLayerReduceFrequency(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = areaWidth + 2;
        int l = areaHeight + 2;
        int[] inLayer = this.parent.getInts(i, j, k, l);
        int[] outLayer = IntCache.getIntCache(areaWidth * areaHeight);

        for (int z = 0; z < areaHeight; ++z)
        {
            for (int x = 0; x < areaWidth; ++x)
            {
                int current = inLayer[x + 1 + (z + 1) * k];
                outLayer[x + z * areaWidth] = current;
                this.initChunkSeed(x + areaX, z + areaY);

                if (current != 0 && this.nextInt(100) < Configs.worldgen.biomeReducer)
                {
                    outLayer[x + z * areaWidth] = 0;
                }
            }
        }

        return outLayer;
    }

}
