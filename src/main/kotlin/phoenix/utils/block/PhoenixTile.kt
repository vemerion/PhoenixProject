package phoenix.utils.block

import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

abstract class PhoenixTile<T : PhoenixTile<T>>(tileEntityTypeIn: TileEntityType<T>) : TileEntity(tileEntityTypeIn)
{
    override fun getUpdateTag(): CompoundNBT = write(CompoundNBT())
}