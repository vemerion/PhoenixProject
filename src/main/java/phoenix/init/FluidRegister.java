package phoenix.init;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import phoenix.fluid.fluids.FluidChorusJuise;
import phoenix.fluid.fluids.FluidKikinJuice;

public class FluidRegister
{
    public static final FluidKikinJuice KININ_FLUID = new FluidKikinJuice();
    public static final FluidChorusJuise CHORUS_FLUID = new FluidChorusJuise();

    public static void register()
    {
        RegisterFluid(KININ_FLUID);
        RegisterFluid(CHORUS_FLUID);
    }
    public static void RegisterFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
