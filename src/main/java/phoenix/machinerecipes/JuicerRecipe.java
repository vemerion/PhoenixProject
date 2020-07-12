package phoenix.machinerecipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import phoenix.init.FluidRegister;

import java.util.ArrayList;
import java.util.List;

public class JuicerRecipe
{
    private static List<JuicerRecipe> recipes = new ArrayList<JuicerRecipe>(); // Лист всех рецептов.
    public static JuicerRecipe EMPTY = new JuicerRecipe(null, null, ItemStack.EMPTY);
    public static List<JuicerRecipe> getRecipes() { // Получатель всех рецептов.
        return recipes;
    }

    private final FluidStack input, output; // Компоненты крафта.
    private final ItemStack inputItem;

    public JuicerRecipe(FluidStack inputIn, FluidStack outputIn, ItemStack stack) { // Конструктор рецепта.
        input = inputIn;
        output = outputIn;
        inputItem = stack;
    }

    public FluidStack getInput() { // Получатель входного предмета рецепта.
        return input;
    }

    public ItemStack getInputItem() { return inputItem; }
    public FluidStack getOutput()   { return output.copy(); }

    public static JuicerRecipe addRecipe(FluidStack input, FluidStack output, ItemStack stack) { // Метод добавления рецепта.
        JuicerRecipe recipe = new JuicerRecipe(input, output, stack); // Создаем рецепт.
        if (recipes.contains(recipe)) // Если он есть уже в рецептах - игнорим.
            return EMPTY;
        recipes.add(recipe); // Если же нет - добавляем.
        return recipe;
    }

    public static JuicerRecipe getRecipe(FluidStack inputFluid, ItemStack inputItem) { // Получатель рецепта через входной предмет.
        if (inputFluid == null || inputFluid.amount == 0 || inputItem.isEmpty() || inputItem.getItem() == Items.AIR)
            return EMPTY;
        for (JuicerRecipe recipe : recipes) // Проходим по списку всех рецептов.
            if (recipe.matchesInput(inputFluid, inputItem)) // Сравниваем входные элементы.
                return recipe; // Возвращаем рецепт, если входные элементы одинаковые.
        return EMPTY;
    }

    public boolean matchesInput(FluidStack is, ItemStack stack) {
        return is.isFluidEqual(input) && stack.getItem() == inputItem.getItem();
    }

    public static void initRecipes() { // Метод регистрации рецептов.
        addRecipe(toStack(FluidRegistry.WATER), toStack(FluidRegister.KIKIN_FLUID), new ItemStack(Items.APPLE, 1));
    }

    private static FluidStack toStack(Fluid fluid) { // Побочный метод.
        return new FluidStack(fluid, 1000);
    }
}
