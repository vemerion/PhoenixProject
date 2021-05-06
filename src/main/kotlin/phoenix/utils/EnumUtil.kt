package phoenix.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*


/*
* It had been stolen from The-Midnight and rewritten into kotlin
*/
object EnumUtil
{
    private var enumConstantsField: Field? = null
    private var enumConstantDirectoryField: Field? = null
    private var constructorAccessorField: Field? = null
    private var modifiersField: Field? = null
    private var valuesFieldName: String? = null

    // Here are some very unsafe hacks to add enums... Only use if you have a very good reason!!!
    // RGSW: I will check if we can replace this with ASM, but for now this should do the work
    private fun <T : Enum<T>> buildEnum
        (
            cls: Class<T>,
            name: String,
            argumentTypes: Array<Class<*>?>,
            vararg arguments: Any
        ): T
    {
        if (enumConstantsField == null)
        {
            enumConstantsField = Class::class.java.getDeclaredField("enumConstants")
            enumConstantsField?.isAccessible = true
        }
        if (enumConstantDirectoryField == null)
        {
            enumConstantDirectoryField = Class::class.java.getDeclaredField("enumConstantDirectory")
            enumConstantDirectoryField?.isAccessible = true
        }
        if (modifiersField == null)
        {
            modifiersField = Field::class.java.getDeclaredField("modifiers")
            modifiersField?.isAccessible = true
        }
        if (constructorAccessorField == null)
        {
            constructorAccessorField = Constructor::class.java.getDeclaredField("constructorAccessor")
            constructorAccessorField?.isAccessible = true
        }
        enumConstantsField!![cls] = null
        enumConstantDirectoryField!![cls] = null
        val valuesField: Field = if (valuesFieldName == null)
        {
            findValuesField(cls, true)
        } else
        {
            try
            {
                cls.getField(valuesFieldName)
            } catch (exc: NoSuchFieldException)
            {
                findValuesField(cls, false)
            }
        }
        valuesField.isAccessible = true
        var modifiers = valuesField.modifiers
        modifiers = modifiers and Modifier.FINAL.inv()
        modifiersField!![valuesField] = modifiers
        val values = valuesField[null] as Array<T>
        val params = arrayOfNulls<Class<*>?>(argumentTypes.size + 2)
        val args = arrayOfNulls<Any>(arguments.size + 2)
        System.arraycopy(argumentTypes, 0, params, 2, argumentTypes.size)
        System.arraycopy(arguments, 0, args, 2, arguments.size)
        params[0] = String::class.java
        params[1] = Int::class.javaPrimitiveType
        args[0] = name
        args[1] = values.size
        val constr = cls.getDeclaredConstructor(*params)
        constr.isAccessible = true
        var ca = constructorAccessorField!![constr]
        if (ca == null)
        {
            val acquireConstructorAccessorMethod =
                Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor")
            acquireConstructorAccessorMethod.isAccessible = true
            ca = acquireConstructorAccessorMethod.invoke(constr)
        }
        val caClass: Class<*> = ca!!.javaClass
        val newInstanceMethod = caClass.getDeclaredMethod("newInstance", Array<Any>::class.java)
        newInstanceMethod.isAccessible = true
        val instance = newInstanceMethod.invoke(ca, args as Any) as T
        val newValues = Arrays.copyOf(values, values.size + 1)
        newValues[values.size] = instance
        valuesField[null] = newValues
        return instance
    }

    private fun <T> findValuesField(cls: Class<T>, cache: Boolean): Field
    {
        val fields = cls.declaredFields
        var valuesField: Field? = null
        for (field in fields)
        {
            if (field.isSynthetic)
            {
                val type = field.type
                if (type.isArray)
                {
                    if (type.componentType == cls)
                    {
                        if (cache) valuesFieldName = field.name
                        valuesField = field
                        break
                    }
                }
            }
        }
        if (valuesField == null)
        {
            throw ReflectiveOperationException("Enum class does not have a values field")
        }
        return valuesField
    }

    fun <T : Enum<T>> addEnum(cls: Class<T>, name: String, params: Array<Class<*>?>, vararg args: Any): T
    {
        return try
        {
            buildEnum(cls, name, params, *args)
        } catch (e: Exception)
        {
            throw RuntimeException("Can't add enum", e)
        }
    }

    fun <T : Enum<T>> addEnum(cls: Class<T>, name: String): T
    {
        return try
        {
            buildEnum(cls, name, arrayOfNulls(0))
        } catch (e: Exception)
        {
            throw RuntimeException("Can't add enum", e)
        }
    }

    @SafeVarargs
    fun <D, T : Enum<T>?> enumSelect(value: T?, vararg items: D): D
    {
        if (value == null) throw NullPointerException()
        return items[value.ordinal]
    }
}