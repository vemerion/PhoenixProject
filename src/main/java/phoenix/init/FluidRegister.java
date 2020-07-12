package phoenix.init;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import phoenix.fluid.fluids.FluidChorusJuiсe;
import phoenix.fluid.fluids.FluidKikinJuice;

public class FluidRegister
{
    public static final FluidKikinJuice KIKIN_FLUID = new FluidKikinJuice();
    public static final FluidChorusJuiсe CHORUS_FLUID = new FluidChorusJuiсe();

    public static void register()
    {
        RegisterFluid(KIKIN_FLUID);
        RegisterFluid(CHORUS_FLUID);
    }
    public static void RegisterFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
