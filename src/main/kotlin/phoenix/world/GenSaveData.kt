package phoenix.world

import net.minecraft.nbt.CompoundNBT
import net.minecraft.world.server.ServerWorld
import net.minecraft.world.storage.WorldSavedData


class GenSaveData : WorldSavedData
{
    private var data: CompoundNBT

    constructor() : super(DATA_NAME)
    {
        data = CompoundNBT()
        data.putBoolean("iscorngened", false)
    }

    //Это второй конструктор, на всякий. А первый нужен для того, чтобы ничего не упало)
    constructor(s: String) : super(s)
    {
        data = CompoundNBT()
        data.putBoolean("iscorngened", false)
    }

    override fun read(nbt: CompoundNBT)
    {
        if (nbt.contains("gen_nbt"))
        {
            data = nbt.getCompound("gen_nbt")
        } else
        {
            val compound = CompoundNBT()
            data.putBoolean("iscorngened", false)
            data = compound
            nbt.put("gen_nbt", compound)
        }
        //Начало костыля
        StageManager.read(nbt)
        //конец костыля
    }

    override fun write(compound: CompoundNBT): CompoundNBT
    {
        compound.put("gen_nbt", data)
        //начало костыля
        StageManager.write(compound)
        //конец костыля
        return compound
    }

    val isCornGenned: Boolean
        get() = data.getBoolean("iscorngened")

    fun setCornGenned()
    {
        data.putBoolean("iscorngened", true)
        markDirty()
    }

    companion object
    {
        lateinit var INSTANCE : GenSaveData
        private const val DATA_NAME = "phoenix_gen"

        //Этим мы получаем экземпляр данных для мира
        operator fun get(world: ServerWorld): GenSaveData
        {
            INSTANCE = world.savedData.getOrCreate(::GenSaveData, DATA_NAME)
            return  INSTANCE
        }
    }
}
