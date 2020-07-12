package phoenix.util;

import phoenix.Phoenix;

public class ConflictExeption extends Exception
{
    public ConflictExeption(String modName, String reason)
    {
        super(reason);
        System.out.println("Мод " + Phoenix.MOD_NAME + " конфликтует с модом " + modName + " по причине " + reason);
    }
}