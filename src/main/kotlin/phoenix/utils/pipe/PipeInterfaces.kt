package phoenix.utils.pipe

import net.minecraftforge.fluids.capability.templates.FluidTank

interface IFluidPipe
{
    var numberInGraph: Int
}

interface IFluidMechanism : IFluidPipe
{
    val input: FluidTank
    val output: FluidTank
}
