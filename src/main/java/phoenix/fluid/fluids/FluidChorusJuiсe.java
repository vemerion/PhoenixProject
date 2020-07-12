package phoenix.fluid.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import phoenix.Phoenix;

public class FluidChorusJuiсe extends Fluid
{
    public FluidChorusJuiсe()
    {
        super("chorus_juice",
                new ResourceLocation(Phoenix.MOD_ID, "fluid/juice_still"),
                new ResourceLocation(Phoenix.MOD_ID, "fluid/juice_flow"));
        setDensity(1100);
        setGaseous(false);
        setLuminosity(5);
        setViscosity(3900);
        setTemperature(300);
        setUnlocalizedName("chorus_juice");
    }
}
