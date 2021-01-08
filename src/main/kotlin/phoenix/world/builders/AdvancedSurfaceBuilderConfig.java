package phoenix.world.builders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;


public class AdvancedSurfaceBuilderConfig implements ISurfaceBuilderConfig
{
    private final BlockState top;
    private final BlockState under;
    private final BlockState underWater;
    private final BlockState advanced;

    public AdvancedSurfaceBuilderConfig(BlockState topMaterial, BlockState underMaterial, BlockState underWaterMaterial, BlockState advancedMaterial)
    {
        this.top        = topMaterial;
        this.under      = underMaterial;
        this.underWater = underWaterMaterial;
        this.advanced   = advancedMaterial;
    }

    public AdvancedSurfaceBuilderConfig(Block topMaterial, Block underMaterial, Block underWaterMaterial, Block advancedMaterial)
    {
        this.top        = topMaterial.getDefaultState();
        this.under      = underMaterial.getDefaultState();
        this.underWater = underWaterMaterial.getDefaultState();
        this.advanced   = advancedMaterial.getDefaultState();
    }

      
    @Override
    public BlockState getTop()
    {
        return top;
    }

      
    @Override
    public BlockState getUnder()
    {
        return under;
    }

    public BlockState getUnderWater()
    {
        return underWater;
    }

    public BlockState getAdvanced()
    {
        return advanced;
    }

    public static final Codec<AdvancedSurfaceBuilderConfig> CODEC =
            RecordCodecBuilder.create(
                    (kind) -> kind.group(
                            BlockState.CODEC.fieldOf("top").forGetter((config) -> config.top),
                            BlockState.CODEC.fieldOf("under").forGetter((config) -> config.under),
                            BlockState.CODEC.fieldOf("underWater").forGetter((config) -> config.underWater),
                            BlockState.CODEC.fieldOf("advanced").forGetter((config) -> config.advanced)
                    ).apply(kind, AdvancedSurfaceBuilderConfig::new));
}
