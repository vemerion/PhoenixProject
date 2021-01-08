package phoenix.tile.redo

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank
import phoenix.init.PhoenixTiles
import phoenix.tile.ash.OvenTile
import phoenix.utils.SerializeUtils
import phoenix.utils.block.PhoenixTile
import phoenix.utils.pipe.IFluidMechanism
import java.util.*

class TankTile : PhoenixTile<OvenTile>(PhoenixTiles.OVEN), IFluidMechanism, ITickableTileEntity
{
    var tank = FluidTank(FluidAttributes.BUCKET_VOLUME * 5)

    override val output: FluidTank = tank
    override val input: FluidTank = tank
    override var numberInGraph: Int = 0
    private var stack = ItemStack.EMPTY


    private val holder = LazyOptional.of<IFluidHandler> { tank }

    override fun tick()
    {
    }

    override fun toString(): String
    {
        var s = "TankTile{ stack= ";
        s += stack;
        s += " number in graph = "
        s += numberInGraph
        s += " tank = "
        s += tank
        return s
    }

    override fun read(state : BlockState, compound : CompoundNBT)
    {
        tank.readFromNBT(compound)
        stack = ItemStack.read(compound)
        numberInGraph = compound.getInt("number_in_graph")
        super.read(state, compound)
    }

    override fun write(tagIn: CompoundNBT): CompoundNBT
    {
        tank.writeToNBT(tagIn)
        stack.write(tagIn)
        tagIn.putInt("number_in_graph", numberInGraph)
        return super.write(tagIn)
    }

    override fun getUpdateTag(): CompoundNBT
    {
        return write(CompoundNBT())
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket
    {
        return UpdatePacket(tank, stack, numberInGraph)
    }

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket)
    {
        val packet = pkt as UpdatePacket
        this.numberInGraph = packet.numberInGraph
        this.tank = packet.tank
        this.stack = packet.stack
    }

    override fun <T> getCapability(capability: Capability<T>, facing: Direction?): LazyOptional<T>
    {
        return if (capability === CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) holder.cast() else super.getCapability(capability, facing)
    }

    class UpdatePacket(var tank: FluidTank, var stack: ItemStack, var numberInGraph: Int) : SUpdateTileEntityPacket()
    {
        override fun writePacketData(buf: PacketBuffer)
        {
            super.writePacketData(buf)
            SerializeUtils.writeToBuf(tank, buf)
            buf.writeItemStack(stack)
            buf.writeInt(numberInGraph)
        }

        override fun readPacketData(buf: PacketBuffer)
        {
            super.readPacketData(buf)
            tank = SerializeUtils.readTank(buf)
            stack = buf.readItemStack()
            numberInGraph = buf.readInt()
        }
    }
}