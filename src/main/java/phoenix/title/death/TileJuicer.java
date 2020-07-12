package phoenix.title.death;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import phoenix.machinerecipes.JuicerRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileJuicer extends TileEntity implements ITickable
{
    private int time = 0;
    protected FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME *  10);
    private ItemStack stack = new ItemStack(Items.AIR);
    public FluidTank getTank()
    {
        return tank;
    }

    public void setTank(FluidTank tank)
    {
        this.tank = tank;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public void setStack(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
        time = tag.getInteger("time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tank.writeToNBT(tag);
        stack.writeToNBT(tag);
        tag.setInteger("time", time);
        return tag;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) tank;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if(world.getTileEntity(pos.up()) instanceof TileTank && world.getTileEntity(pos.down()) instanceof TileTank && stack.getItem() != Items.AIR)
        {
            TileTank tankUp = (TileTank) world.getTileEntity(pos.up());
            TileTank tankDown = (TileTank) world.getTileEntity(pos.up());
            JuicerRecipe recipe = JuicerRecipe.getRecipe(tankUp.getTank().getFluid(), stack);
            if(time >= 1000)
            {
                if(tankDown.getTank().canFillFluidType(recipe.getOutput()))
                {
                    tankDown.getTank().fill(recipe.getOutput(), true);
                }
            }
            else
            {
                time++;
            }
        }
    }
}

