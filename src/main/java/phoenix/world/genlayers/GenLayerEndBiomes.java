package phoenix.world.genlayers;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import phoenix.world.BiomeRegister;

public class GenLayerEndBiomes extends GenLayer
{
	private final int SKY_ID;
    private final int END_UNDER_ID;
	private final static int MAIN_ISLAND_SIZE = (int) (80 / Math.pow(2, 8));;

    public GenLayerEndBiomes(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;  
		SKY_ID = Biome.getIdForBiome(Biomes.SKY);
        END_UNDER_ID = Biome.getIdForBiome(BiomeRegister.END_UNDER);
    }
    
    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] inLayer = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] outLayer = IntCache.getIntCache(areaWidth * areaHeight);
    
        for (int y = 0; y < areaHeight; ++y)
        {
            for (int z = 0; z < areaWidth; ++z)
            {
            	this.initChunkSeed(z + areaX, y + areaY);
                int biomeInt = inLayer[z + y * areaWidth];

                if(biomeInt == 0 || (areaX < MAIN_ISLAND_SIZE && areaX > -MAIN_ISLAND_SIZE && areaY < MAIN_ISLAND_SIZE && areaY > -MAIN_ISLAND_SIZE))
                {
                	outLayer[z + y * areaWidth] = SKY_ID;
                }
                else if(biomeInt == 1)
                {
                    outLayer[z + y * areaWidth] = END_UNDER_ID;
                }
                else
                {
                    //System.out.println("Shit: biome id " + biomeInt + " found in genlayer");
                	outLayer[z + y * areaWidth] = SKY_ID;
                }
            	
            }
            
        }
        
        return outLayer;
    
    }
	
}
