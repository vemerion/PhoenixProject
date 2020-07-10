package phoenix.world.structures.Unit01;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import phoenix.Phoenix;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MapGenCorn extends MapGenStructure
{
    private static final ResourceLocation CORN = new ResourceLocation(Phoenix.MOD_ID + ":temple_end");
    @Override
    public String getStructureName()
    {
        return "Corn";
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return MathHelper.abs(chunkX) == 64 && MathHelper.abs(chunkZ) == 64;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
       // return new Start(world, rand, chunkX, chunkZ);
        return new Start();
    }

    @Override
    public void generate(World worldIn, int x, int z, ChunkPrimer primer)
    {
        final PlacementSettings settings = new PlacementSettings().setRotation(Rotation.NONE);
        final Template template = worldIn.getSaveHandler().getStructureTemplateManager().getTemplate(worldIn.getMinecraftServer(), CORN);
        template.addBlocksToWorld(worldIn, new BlockPos(
                        x,
                        100,
                        z),
                settings);
    }

    public static class Start extends StructureStart
    {
        int x, z;
        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            x = chunkX * 16;
            z = chunkZ * 16;
            final PlacementSettings settings = new PlacementSettings().setRotation(Rotation.NONE);
            final Template template = worldIn.getSaveHandler().getStructureTemplateManager().getTemplate(worldIn.getMinecraftServer(), CORN);
            template.addBlocksToWorld(worldIn, new BlockPos(
                    chunkX - random.nextInt(200),
                    100 + random.nextInt(80) - 30,
                    chunkZ - random.nextInt(200)),
                    settings);
        }

        @Nonnull
        @Override
        public StructureBoundingBox getBoundingBox()
        {
            return new StructureBoundingBox(x, 0, z, x + 16, 16, z + 16);
        }
    }
}
