package phoenix.blocks.rebirth;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoenix.init.BlocksRegister;

import java.util.Random;

public class BlockKikiNFruit extends Block
{
    public BlockKikiNFruit()
    {
        super(Material.LEAVES);
        setRegistryName("kinin_fruit");
        setTranslationKey("kinin_fruit");
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        label:
        {
            if (isComplitly(pos.up(), worldIn) && isComplitly(pos.down(), worldIn)
                    && isComplitly(pos.west(), worldIn) && isComplitly(pos.east(), worldIn)
                    && isComplitly(pos.south(), worldIn) && isComplitly(pos.north(), worldIn)) {
                if (rand.nextInt(1000) > 600) {
                    BlockPos pos1 = pos;
                    switch (rand.nextInt(4)) {
                        case 0:
                            pos1 = pos.south();
                            break;
                        case 1:
                            pos1 = pos.north();
                            break;
                        case 2:
                            pos1 = pos.east();
                            break;
                        case 3:
                            pos1 = pos.west();
                            break;
                    }
                    if (worldIn.isAirBlock(pos1) && pos1.getY() != 0) {
                        worldIn.setBlockState(pos, BlocksRegister.KIKIN_PLANT.getDefaultState());
                        worldIn.setBlockState(pos1, BlocksRegister.KIKIN_FRUIT.getDefaultState());
                    }
                    if (rand.nextInt(10) == 2) {
                        break label;
                    }
                }
                else {
                    if (worldIn.isAirBlock(pos.down()) && pos.down().getY() != 0) {
                        worldIn.setBlockState(pos, BlocksRegister.KIKIN_PLANT.getDefaultState());
                        worldIn.setBlockState(pos.down(), BlocksRegister.KIKIN_FRUIT.getDefaultState());
                    }
                }
            }

            else {
                worldIn.setBlockToAir(pos);
            }
        }
    }

    private boolean isComplitly(BlockPos pos, World world)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().equals(BlocksRegister.KIKIN_PLANT) ||  state.getBlock().equals(BlocksRegister.FERTILE_END_STONE) || state.getBlock().equals(Blocks.AIR) ;
    }
}
