package phoenix.utils;

import net.minecraft.item.crafting.IRecipe;

import java.util.List;

/**
 * It had stolen from TConstruct
 */
/**
 * This interface is intended to be used on dynamic recipes to return a full list of valid recipes.
 * @param <T>  Recipe type for the return
 */
public interface IMultiRecipe<T extends IRecipe<?>> {
    /**
     * Gets a list of recipes for display in JEI
     * @return  List of recipes
     */
    List<T> getRecipes();
}

