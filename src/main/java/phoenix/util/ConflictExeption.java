package phoenix.util;

public class ConflictExeption extends Exception
{
    public ConflictExeption(String reason)
    {
        super(reason);
        System.out.println("Мод что-то сломал по причине " + reason);
    }
}