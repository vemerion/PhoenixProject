package phoenix.title;

import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

import java.util.Random;

public class TileGost extends TileEntity implements ITickable
{
    private int count;
    int timer = -1;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("count", this.count);
        return super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        this.count = tagCompound.getInteger("count");
        super.readFromNBT(tagCompound);
    }

    public int getCount() {  return this.count;  }

    public void incrementCount()
    {
        this.count++;
        this.markDirty();
    }

    public void decrementCount()
    {
        this.count--;
        this.markDirty();
    }

    public void setTimer(int timer)
    {
        this.timer = timer;
    }

    public int getTimer()
    {
        return timer;
    }

    @Override
    public void update()
    {
        if(timer > 0) timer--;
        else if(timer == 0)
        {
            switch (count) {
                case -7:
                    world.setBlockToAir(pos);
                    break;
                case 9:
                    EntityFallingBlock entity = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockState(pos));
                    entity.motionY = 0;
                    entity.motionX = (1 - world.rand.nextInt(2)) / 4F;
                    entity.motionZ = (1 - world.rand.nextInt(2)) / 4F;
                    entity.moveToBlockPosAndAngles(pos, 0F, 0F);
                    entity.setHurtEntities(true);
                    world.spawnEntity(entity);
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                    break;
                default:
                    switch (new Random().nextInt(4)) {
                        case 0:
                            EntityFallingBlock entity2 = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockState(pos));
                            entity2.motionY = 0;
                            entity2.motionX = (1 - world.rand.nextInt(2)) / 4F;
                            entity2.motionZ = (1 - world.rand.nextInt(2)) / 4F;
                            entity2.moveToBlockPosAndAngles(pos, 0F, 0F);
                            entity2.setHurtEntities(true);
                            world.spawnEntity(entity2);
                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                            break;
                        case 1:
                            world.setBlockToAir(pos);
                            break;
                        default:
                            break;
                    }
            }
        }
    }
}
