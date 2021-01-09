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
class PipeTile() : PhoenixTile<PotteryBarrelTile>(PhoenixTiles.POTTERY_BARREL), IFluidPipe
{
    private var number_in_graph = 0

    override var numberInGraph
        get() = number_in_graph
        set(value) {
            number_in_graph = value
        }
    override fun getBlockState(): BlockState = world!!.getBlockState(pos)

    override fun read(state: BlockState, tag: CompoundNBT)
    {
        super.read(state, tag)
        number_in_graph = tag.getInt("number_in_graph")
    }

    override fun write(tag: CompoundNBT): CompoundNBT
    {
        var tag = tag
        tag = super.write(tag)
        tag.putInt("number_in_graph", number_in_graph)
        return tag
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket = UpdatePacket(number_in_graph)

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket)
    {
        number_in_graph = (pkt as UpdatePacket).numberInGraph
    }

    internal class UpdatePacket(var numberInGraph: Int) : SUpdateTileEntityPacket()
    {
        override fun readPacketData(buf: PacketBuffer)
        {
            numberInGraph = buf.readInt()
            super.readPacketData(buf)
        }

        override fun writePacketData(buf: PacketBuffer)
        {
            buf.writeInt(numberInGraph)
            super.writePacketData(buf)
        }
    }
}
