package phoenix.utils

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fluids.capability.templates.FluidTank


object SerializeUtils
{
    fun writeToBuf(tank: FluidTank, buf: PacketBuffer)
    {
        tank.fluid.writeToPacket(buf)
        buf.writeInt(tank.capacity)
    }

    fun readTank(buf: PacketBuffer): FluidTank
    {
        val stack = buf.readFluidStack()
        val capacity = buf.readInt()
        val res = FluidTank(capacity)
        res.fluid = stack
        return res
    }
}