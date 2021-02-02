package phoenix;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Logger
{
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static void log(String message)
    {
        Exception e = new Exception();
        StackTraceElement[] trace = e.getStackTrace();

        String from = last(trace[1].getClassName().split("."));
        logger.log(Level.DEBUG, "<" + from + "> " + message);
    }

    public static void error(String message)
    {
        Exception e = new Exception();
        StackTraceElement[] trace = e.getStackTrace();

        String from = last(trace[1].getClassName().split("."));
        logger.error("<" + from + "> " + message);
    }

    private static <T> T last(T[] array)
    {
        return array[array.length - 1];
    }
}


