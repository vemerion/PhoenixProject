package phoenix.tile.redo

import mcp.MethodsReturnNonnullByDefault
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import phoenix.init.PhoenixTiles
import phoenix.tile.ash.PotteryBarrelTile
import phoenix.utils.block.PhoenixTile
import phoenix.utils.pipe.IFluidPipe
import javax.annotation.ParametersAreNonnullByDefault


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class PipeTile : PhoenixTile<PotteryBarrelTile>(PhoenixTiles.POTTERY_BARREL), IFluidPipe
{
    override fun getBlockState(): BlockState = world!!.getBlockState(pos)

    override fun read(state: BlockState, tag: CompoundNBT)
    {
        super.read(state, tag)
    }

    override fun write(tag: CompoundNBT): CompoundNBT
    {
        var tag = tag
        tag = super.write(tag)
        return tag
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket = UpdatePacket()

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket)
    {
    }

    internal class UpdatePacket : SUpdateTileEntityPacket()
    {
        override fun readPacketData(buf: PacketBuffer)
        {
            super.readPacketData(buf)
        }

        override fun writePacketData(buf: PacketBuffer)
        {
            super.writePacketData(buf)
        }
    }
}
