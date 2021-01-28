package phoenix.utils

import org.apache.logging.log4j.Level
import phoenix.Phoenix
import phoenix.init.PhoenixConfiguration

object LogManager
{
    @JvmStatic
    fun log(obj : Any, message : String)
    {
        if(PhoenixConfiguration.COMMON_CONFIG.debug.get())
        {
            Phoenix.LOGGER.error("${obj.javaClass} $message")
        }
        else
        {
            Phoenix.LOGGER.log(Level.DEBUG, "<${obj.javaClass.lastName()}> $message")
        }
    }

    @JvmStatic fun error(from : String, message : String?)   = Phoenix.LOGGER.error("$from ${message ?: ""}")
    @JvmStatic fun error(obj  : Any,    message : String?)   = Phoenix.LOGGER.error("<${obj.javaClass.lastName()}> ${message ?: ""}")
    @JvmStatic fun error(obj  : Any,    message : Exception?)= Phoenix.LOGGER.error(if(message != null) "Exception in class <${obj.javaClass.lastName()}>: $message" else "Null exception in class <${obj.javaClass.lastName()}>")

    @JvmStatic
    fun errorObjects(obj : Any, vararg objects : Any)
    {
        var message = ""
        for (i in objects)
            message += " $i"
        error(obj, message)
    }
}