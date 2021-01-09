package phoenix.utils.pipe

import net.minecraftforge.fluids.capability.templates.FluidTank

interface IFluidPipe
{
}

interface IFluidMechanism : IFluidPipe
{
    val input: FluidTank
    val output: FluidTank
}
