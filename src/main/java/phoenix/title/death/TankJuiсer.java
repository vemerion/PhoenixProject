package phoenix.title.death;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class TankJuiсer extends FluidTank
{
    @Nullable
    protected FluidStack input, output;
    protected int input_capacity, output_capacity;
    protected TileJuicer tile;

    public TankJuiсer(int capacity)
    {
        super(capacity);
    }

    @Nullable
    public FluidStack getInput()
    {
        return input;
    }

    public void setInput(@Nullable FluidStack input)
    {
        this.input = input;
    }

    @Nullable
    public FluidStack getOutput()
    {
        return output;
    }

    public void setOutput(@Nullable FluidStack output)
    {
        this.output = output;
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt)
    {
        if (!nbt.hasKey("Empty")) {
            FluidStack fluid = loadFluidStackFromNBT(nbt, "input");
            setInput(fluid);
            fluid = loadFluidStackFromNBT(nbt, "output");
            setOutput(fluid);
        }
        else {
            setInput(null);
            setOutput(null);
        }
        return this;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        if (fluid != null) {
            input.writeToNBT(nbt);
            output.writeToNBT(nbt);
        }
        else {
            nbt.setString("Empty", "");
        }
        return nbt;
    }


    public static NBTTagCompound writeToNBT(NBTTagCompound nbt, FluidStack stack, String key)
    {
        nbt.setString(key + "_FluidName", FluidRegistry.getFluidName(stack.getFluid()));
        nbt.setInteger(key + "_Amount", stack.amount);

        if (nbt != null) {
            nbt.setTag(key + "_Tag", nbt);
        }
        return nbt;
    }

    public static FluidStack loadFluidStackFromNBT(NBTTagCompound nbt, String key)
    {
        if (nbt == null) {
            return null;
        }
        if (!nbt.hasKey(key + "_FluidName", Constants.NBT.TAG_STRING)) {
            return null;
        }

        String fluidName = nbt.getString(key + "_FluidName");
        if (FluidRegistry.getFluid(fluidName) == null) {
            return null;
        }
        FluidStack stack = new FluidStack(FluidRegistry.getFluid(fluidName), nbt.getInteger(key + "_Amount"));

        if (nbt.hasKey(key + "_Tag")) {
            stack.tag = nbt.getCompoundTag(key + "_Tag");
        }
        return stack;
    }
}