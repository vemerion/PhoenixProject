package phoenix.utils

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object LogManager
{
    private val LOGGER : Logger = LogManager.getLogger()!!

    @JvmStatic
    fun errorObjects(vararg objects : Any)
    {
        var message = ""
        for (i in objects)
            message += " $i"
        val e = Exception()
        val from = e.stackTrace[1].className.split(".").last()
        LOGGER.error("<$from> $message")
    }


    @JvmStatic
    fun debug(message : String)
    {
        val e = Exception()
        val from = e.stackTrace[1].className.split(".").last()
        LOGGER.debug("<$from> $message")
    }

    @JvmStatic
    fun error(message : String)
    {
        val e = Exception()
        val from = e.stackTrace[2].className.split(".").last()
        LOGGER.error("<$from> $message")
    }

    @JvmStatic
    fun error(obj : Any)
    {
        val e = Exception()
        val from = e.stackTrace[2].className.split(".").last()
        LOGGER.error("<$from> $obj")
    }

    @JvmStatic
    fun error(from : String, message : String) = LOGGER.error("<$from> $message")

    @JvmStatic
    fun error(message : Exception?)
    {
        val e = Exception()
        val from = e.stackTrace[1].className.split(".").last()
        LOGGER.error(if(message != null) "Exception in class <$from>: $message" else "Null exception in class <$from>")
    }
}