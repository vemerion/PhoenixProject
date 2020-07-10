package phoenix.world.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import phoenix.init.BlocksRegister;

import java.util.Random;

public class BiomeEndUnder extends Biome
{
    private static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
    public static BiomeProperties properties = new BiomeProperties("Under-wold").setTemperature(Biomes.SKY.getDefaultTemperature()).setRainfall(Biomes.SKY.getRainfall()).setRainDisabled();

    public BiomeEndUnder()
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = END_STONE;
        this.fillerBlock = END_STONE;
        this.decorator = new BiomeEndDecorator();
    }

    @Override
    public BiomeDecorator createBiomeDecorator(){ return new BiomeDecoratorEndBiomes(); }

    public void decorate(World world, Random rand, BlockPos pos)
    {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = getEndDownSurfaceHeight(world, x, z);
                if (height != 0) {
                    for (int i = height; i < height + 8; i++) {
                        if (world.getBlockState(new BlockPos(pos.add(x, i, z))).getBlock().equals(Blocks.END_STONE)) {
                            world.setBlockState(new BlockPos(pos.add(x, i, z)), BlocksRegister.FERTILE_END_STONE.getDefaultState());
                        }
                    }
                }
            }
        }
        //System.out.println("Under generated at " + pos.getX() + " " + pos.getZ());
        super.decorate(world, rand, pos);
    }

    public static int getEndDownSurfaceHeight(World world, int x, int z)
    {
        for (int i = 10; i < 30; i++)
            if(!world.isAirBlock(new BlockPos(x, i, z)))
                return i;

        return 0;
    }
}
