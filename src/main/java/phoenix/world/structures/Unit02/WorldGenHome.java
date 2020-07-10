package phoenix.world.structures.Unit02;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;
import phoenix.Phoenix;
import phoenix.world.GostTemplateProcessor;

import java.util.Random;

public class WorldGenHome implements IWorldGenerator
{
    private static final ResourceLocation HOME = new ResourceLocation(Phoenix.MOD_ID + ":end_house_small");
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[random.nextInt(4)]);
        settings.setIntegrity(0.9F);
        final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), HOME);
        if(random.nextInt(10000) == 15 && world.getCapability(Phoenix.STAGER_CAPABILITY, null).getStage() >= 2) {
            BlockPos pos = new BlockPos(chunkX * 16 + 8, 80 + random.nextInt(20), chunkZ * 16 + 8);
            if(world.isAirBlock(pos) && world.isAirBlock(pos.west()) && world.isAirBlock(pos.east()) && world.isAirBlock(pos.up()) && world.isAirBlock(pos.down())
                    && world.isAirBlock(pos.north()) && world.isAirBlock(pos.south()))
            //template.addBlocksToWorld(world, pos, settings);
            template.addBlocksToWorld(world, pos, new GostTemplateProcessor(), settings, 0);
        }
    }
}
