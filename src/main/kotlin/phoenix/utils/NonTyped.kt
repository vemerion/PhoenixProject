package phoenix.utils

import com.google.gson.JsonObject
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import phoenix.Phoenix
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import java.lang.Math.sqrt
import java.util.*

data class Pair<M, V>(var v : V, var m : M)
data class Tuple<M, V, K>(var first : V, var second : M, var third : K)

fun World.destroyBlock(pos : BlockPos, shouldDrop : Boolean, entity : Entity?, stack : ItemStack) : Boolean
{
    val state = this.getBlockState(pos)
    return if (state.isAir(this, pos))
    {
        false
    } else
    {
        val fluidState = this.getFluidState(pos)
        if (shouldDrop)
        {
            val tile = if (state.hasTileEntity()) this.getTileEntity(pos) else null
            try
            {
                Block.spawnDrops(state, this, pos, tile, entity, stack)
            }catch (e : Exception){}

        }
        this.setBlockState(pos, fluidState.blockState, 3)
    }
}

fun JsonObject.getFloat(nameIn: String, fallback : Float)           = JSONUtils.getFloat (this, nameIn, fallback)
fun JsonObject.getInt(nameIn: String)                               = JSONUtils.getInt   (this, nameIn)
fun JsonObject.getString(nameIn: String, fallback : String): String = JSONUtils.getString(this, nameIn, fallback)
fun JsonObject.readItemStack(nameIn: String): ItemStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(this, nameIn))

fun ItemStack.getEnchantmentLevel(enchantment: Enchantment) = EnchantmentHelper.getEnchantmentLevel(enchantment, this)

fun IWorld.getDownHeight(pos : BlockPos, max: Int): BlockPos
{
    val pos2 = BlockPos(pos.x, 0, pos.z)
    for (i in 0 until max)
    {
        if (!this.isAirBlock(pos2.add(0, i, 0))) return pos2.add(0, i - 1, 0)
    }
    return pos
}

fun Random.nextInt(min : Int, max : Int) = (min - 0.5 + this.nextDouble() * (max - min + 1)).toInt()

fun<T> Class<T>.lastName() = name.split(".").last()
fun<T> Array<T>.last() = this[size - 1]
fun<T> List<T> .last() = this[size - 1]

fun PacketBuffer.writeDate(date : Date)
{
    this.writeInt(date.minute)
    this.writeInt(date.day)
    this.writeInt(date.year)
}

fun PacketBuffer.readDate() : Date
{
    val res = Date(0, 0, 0)
    res.minute = readInt()
    res.day = readInt()
    res.year = readInt()
    return res;
}

operator fun<T> ArrayList<T>.plusAssign(t : T)
{
    this.add(t)
}


operator fun BlockPos.minus(s : BlockPos) : Double
{
    return sqrt(((s.x - x) * (s.x - x) + (s.y - y) * (s.y - y) + (s.z - z) * (s.z - z)).toDouble())
}

infix fun Int.until(int : Int) = this..int-1

fun Biome.getId(): Int
{
    return (ForgeRegistries.BIOMES as ForgeRegistry<Biome>).getID(this)
}

fun RegistryKey<Biome>.getId() : Int
{
    return (ForgeRegistries.BIOMES as ForgeRegistry<Biome>).getID(this.registryName)
}

fun<T : IForgeRegistryEntry<T>> KDeferredRegister<T>.register(name : String, t : T) = register(name) {t}

fun<T> IForgeRegistryEntry<T>.setRegistryName(nameIn: String) = setRegistryName(ResourceUtils.key(nameIn))

fun<T> IForgeRegistryEntry<T>.validateRegistryName(nameIn: String)
{
    if(registryName == null) registryName = ResourceUtils.key(nameIn)
}

fun<T> IForgeRegistryEntry<T>.validateRegistryName(nameIn: ResourceLocation)
{
    if(registryName == null) registryName = nameIn
}

