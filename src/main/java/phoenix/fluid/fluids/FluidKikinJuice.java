package phoenix.fluid.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import phoenix.Phoenix;

public class FluidKikinJuice extends Fluid
{
    public FluidKikinJuice()
    {
        super("kikin_juise",
                new ResourceLocation(Phoenix.MOD_ID, "fluid/juise_still"),
                new ResourceLocation(Phoenix.MOD_ID, "fluid/juise_flow"));
        setDensity(1100);
        setGaseous(false);
        setLuminosity(5);
        setViscosity(3900);
        setTemperature(300);
        setUnlocalizedName("kikin_juise");
    }
}
