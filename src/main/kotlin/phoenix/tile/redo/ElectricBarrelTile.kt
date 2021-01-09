package phoenix.tile.redo

import net.minecraft.tileentity.ITickableTileEntity
import phoenix.init.PhoenixTiles
import phoenix.utils.block.PhoenixTile


class ElectricBarrelTile(): PhoenixTile<ElectricBarrelTile>(PhoenixTiles.ELECTRIC_BARREL), ITickableTileEntity
{
    override fun tick()
    {
    }
}