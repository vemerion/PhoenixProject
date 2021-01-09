package phoenix.world.feature

import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.BlockStateProperties.AGE_0_3
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import phoenix.init.PhoenixBlocks
import phoenix.utils.getDownHeight
import java.util.*

object SetaFeature : Feature<NoFeatureConfig>(NoFeatureConfig.field_236558_a_)
{
    override fun func_241855_a(world: ISeedReader, generator: ChunkGenerator, random: Random, pos: BlockPos, config: NoFeatureConfig): Boolean
    {
        if (random.nextInt(3) == 0)
        {
            var position = world.getDownHeight(pos.add(random.nextInt(15), 0, random.nextInt(15)), 50)
            if(position.y > 2)
            {
                world.setBlockState(position, PhoenixBlocks.SETA.defaultState.with(AGE_0_3, random.nextInt(3)), 2)
                if (random.nextInt(3) == 0)
                {
                    position = world.getDownHeight(position.add(random.nextInt(1) - 2, 0, random.nextInt(1) - 2), 30)
                    if(position.y > 2)
                        world.setBlockState   (
                            position,
                            PhoenixBlocks.SETA.defaultState
                                .with(AGE_0_3, random.nextInt(3))
                                .with(BlockStateProperties.HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.random(random)), 2)
                }
            }
        }
        return true
    }
}