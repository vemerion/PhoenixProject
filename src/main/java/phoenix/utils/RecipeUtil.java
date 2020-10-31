package phoenix.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * It had stolen from TConstruct
 */
public class RecipeUtil
{
    /**
     * Gets a recipe by name from the manager
     *
     * @param manager Recipe manager
     * @param name    Recipe name
     * @param clazz   Output class
     * @param <C>     Return type
     * @return Recipe in the given type, or null if wrong type
     */
    public static <C extends IRecipe<?>> Optional<C> getRecipe(RecipeManager manager, ResourceLocation name, Class<C> clazz)
    {
        return manager.getRecipe(name).filter(clazz::isInstance).map(clazz::cast);
    }

    /**
     * Gets a list of all recipes from the manager, safely casting to the specified type. Multi Recipes are kept as a single recipe instance
     *
     * @param manager Recipe manager
     * @param type    Recipe type
     * @param clazz   Preferred recipe class type
     * @param <I>     Inventory interface type
     * @param <T>     Recipe class
     * @param <C>     Return type
     * @return List of recipes from the manager
     */
    public static <I extends IInventory, T extends IRecipe<I>, C extends T> List<C> getRecipes(RecipeManager manager, IRecipeType<T> type, Class<C> clazz)
    {
        return manager.getRecipes(type).values().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of recipes for display in a UI list, such as UI buttons. Will be sorted and filtered
     *
     * @param manager Recipe manager
     * @param type    Recipe type
     * @param clazz   Preferred recipe class type
     * @param filter  Filter for which recipes to add to the list
     * @param <I>     Inventory interface type
     * @param <T>     Recipe class
     * @param <C>     Return type
     * @return Recipe list
     */
    public static <I extends IInventory, T extends IRecipe<I>, C extends T> List<C> getUIRecipes(RecipeManager manager, IRecipeType<T> type, Class<C> clazz, Predicate<? super C> filter)
    {
        return manager.getRecipes(type).values().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(filter)
                .sorted(Comparator.comparing(IRecipe::getId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of all recipes from the manager, expanding multi recipes. Intended for use in recipe display such as JEI
     *
     * @param manager Recipe manager
     * @param type    Recipe type
     * @param clazz   Preferred recipe class type
     * @param <I>     Inventory interface type
     * @param <T>     Recipe class
     * @param <C>     Return type
     * @return List of flattened recipes from the manager
     */
    public static <I extends IInventory, T extends IRecipe<I>, C extends T> List<C> getJEIRecipes(RecipeManager manager, IRecipeType<T> type, Class<C> clazz)
    {
        return manager.getRecipes(type).values().stream()
                .sorted((r1, r2) ->
                {
                    // if one is multi, and the other not, the multi recipe is larger
                    boolean m1 = r1 instanceof IMultiRecipe<?>;
                    boolean m2 = r2 instanceof IMultiRecipe<?>;
                    if (m1 && !m2)
                    {
                        return 1;
                    }
                    if (!m1 && m2)
                    {
                        return -1;
                    }
                    // fall back to recipe ID
                    return r1.getId().compareTo(r2.getId());
                })
                .flatMap((recipe) ->
                {
                    // if its a multi recipe, extract child recipes and stream those
                    if (recipe instanceof IMultiRecipe<?>)
                    {
                        return ((IMultiRecipe<?>) recipe).getRecipes().stream();
                    }
                    return Stream.of(recipe);
                })
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }
}
