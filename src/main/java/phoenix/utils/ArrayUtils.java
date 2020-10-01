package phoenix.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUtils
{
    static public <T extends Object> ArrayList<T> sumArrays(ArrayList<T> first, ArrayList<T> second, T... adv)
    {
        ArrayList<T> res = new ArrayList(first);
        res.addAll(second);

        res.addAll(Arrays.asList(adv));
        return res;
    }

    @SafeVarargs
    static public <T extends Object> ArrayList<T> sumArrays(ArrayList<T> first, T... adv)
    {
        ArrayList<T> res = new ArrayList<>(first);
        res.addAll(Arrays.asList(adv));
        return res;
    }
}
