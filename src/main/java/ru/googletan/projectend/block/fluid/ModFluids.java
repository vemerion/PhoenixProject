package ru.googletan.projectend.block.fluid;

 
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import ru.googletan.projectend.Projectend;

public class ModFluids 
{
	
	public static final EnderFluid ACID = (EnderFluid) new EnderFluid("endacid",
			new ResourceLocation(Projectend.MOD_ID, "endacid_still"),
			new ResourceLocation(Projectend.MOD_ID, "endacid_flow"))
					.setHasBucket(true)
					.setDensity(1100)
					.setGaseous(false)
					.setLuminosity(5)
					.setViscosity(900)
					.setTemperature(300)
					.setUnlocalizedName("endacid");

	public static void registerFluids()
    {
		System.out.println("Registering acid");
		FluidRegistry.registerFluid(ACID);
		FluidRegistry.addBucketForFluid(ACID); 
//        if (ACID.isBucketEnabled())
//        {
//            FluidRegistry.addBucketForFluid(ACID);
//        }
    }
}
