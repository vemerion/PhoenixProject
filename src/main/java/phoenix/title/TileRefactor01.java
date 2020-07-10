package phoenix.title;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileRefactor01 extends TileEntity
{
    private int count;

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
}
