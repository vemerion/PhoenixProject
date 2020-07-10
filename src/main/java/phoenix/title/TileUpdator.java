package phoenix.title;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import phoenix.Phoenix;
import phoenix.client.sound.PESounds;

public class TileUpdator extends TileEntity implements ITickable
{
    @Override
    public void update()
    {
        BlockPos posbase = pos.down();
        BlockPos[] blocks = {posbase, posbase.south(), posbase.north(), posbase.east(), posbase.west(), posbase.east().south(),  posbase.east().north(), posbase.west().south(), posbase.west().north()};
        for (BlockPos block : blocks) if (world.getBlockState(block).getBlock() != Blocks.STAINED_GLASS)  return;
        world.setBlockToAir(pos);
        for (BlockPos block : blocks) world.setBlockToAir(block);

        if (world.getCapability(Phoenix.STAGER_CAPABILITY, null).getStageIn() > 3)
        {
            world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false).rayTrace(100, 10);
            world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false).sendStatusMessage(new TextComponentTranslation("phoenix.massage.newstage"), true);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), PESounds.trans[world.getCapability(Phoenix.STAGER_CAPABILITY, null).getStage() - 1], SoundCategory.MUSIC,  1, 1, false);
            world.getCapability(Phoenix.STAGER_CAPABILITY,  null).setStageIn(1);
            world.getCapability(Phoenix.STAGER_CAPABILITY, null).addStage();
        }
        else
        {
            world.getCapability(Phoenix.STAGER_CAPABILITY, null).addStageIn();
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MUSIC,  1, 1, false);
        }
    }
}
