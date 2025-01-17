package phoenix.tile.ash

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.math.BlockPos
import phoenix.blocks.ash.OvenBlock
import phoenix.containers.ash.OvenContainer
import phoenix.init.PhxItems
import phoenix.init.PhxTiles
import phoenix.network.NetworkHandler
import phoenix.network.SyncOvenPacket
import phoenix.recipes.OvenRecipe.Companion.recipesFromInputs
import phoenix.utils.BlockPosUtils.minus
import phoenix.utils.block.PhoenixTile
import phoenix.utils.get
import phoenix.utils.set
import java.lang.Integer.max
import java.lang.Integer.min

class OvenTile : PhoenixTile(PhxTiles.oven), ITickableTileEntity, IInventory by Inventory(5)
{
    var timers = IntArray(4)
    var burnTime = 0
    private val maxBurnTime = 20 * 60

    init
    {
        timers[0] = -1
        timers[1] = -1
        timers[2] = -1
        timers[3] = -1
    }

    fun outOtherItems() : List<ItemStack>
    {
        val res = ArrayList<ItemStack>()
        var has = false
        for (i in 0..3)
        {
            if(!recipesFromInputs.contains(this[i].item) || this[i].item == PhxItems.COOKED_SETA)
            {
                res.add(this[i].copy())
                this[i] = ItemStack.EMPTY
                timers[i] = 0
                has = true
            }
        }
        if(has)
            NetworkHandler.sendToAll(SyncOvenPacket(this))
        return res
    }

    fun getOtherItems() : List<ItemStack>
    {
        val res = ArrayList<ItemStack>()
        for (i in 0..3)
        {
            if(!recipesFromInputs.contains(this[i].item))
            {
                res.add(this[i]);
            }
        }
        return res
    }

    fun addItem(stack : ItemStack) : Boolean
    {
        for (i in 0..3)
            if(this[i].item == Items.AIR)
            {
                this[i] = ItemStack(stack.item)
                timers[i] = 0
                return true
            }
        return false
    }

    override fun tick()
    {
        val world = world!!
        if(!world.isRemote)
        {
            if(burnTime > 0)
            {
                burnTime--
                burnTime = min(maxBurnTime, burnTime)
                burnTime = max(0, burnTime)
                if(world[pos, OvenBlock.STATE] == 2)
                {
                    var has = false
                    for (i in 0..3)
                    {
                        val current = this[i]
                        if (recipesFromInputs.contains(current.item))
                        {
                            val recipe = recipesFromInputs[current.item]!!
                            val cookTime: Int = recipe.cookTime
                            timers[i]++
                            if (timers[i] >= cookTime)
                            {
                                this[i] = recipe.result
                                has = true
                                timers[i] = 0
                            }
                        }
                    }
                    if (has)
                        NetworkHandler.sendToDim(SyncOvenPacket(this), world.dimension.type)
                }
                if (burnTime == 0)
                    world[pos, OvenBlock.STATE] = 0
            }
        }
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket = UpdatePacket(timers, burnTime, this, pos)

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket)
    {
        timers = (pkt as UpdatePacket).timers
        burnTime = pkt.burnTime
    }

    override fun write(compound: CompoundNBT): CompoundNBT?
    {
        compound.putIntArray("timers", timers)
        compound.putInt("burn_time", burnTime)

        val nbt = CompoundNBT()
        for (i in 0..3)
        {
            val stack = CompoundNBT()
            this[i].write(stack)
            nbt.put("slot$i", stack)
        }

        compound.put("container", nbt)

        return super.write(compound)
    }


    override fun read(compound: CompoundNBT)
    {
        timers = compound.getIntArray("timers")
        burnTime = compound.getInt("burn_time")
        val nbt = compound.getCompound("container")
        for (i in 0..3)
        {
            val current = nbt.getCompound("slot$i")
            this[i] = ItemStack.read(current)
        }
        super.read(compound)
    }


    class UpdatePacket(var timers: IntArray, var burnTime : Int, var container: OvenTile, pos: BlockPos) : SUpdateTileEntityPacket(pos, 32, CompoundNBT())
    {
        override fun writePacketData(buf: PacketBuffer)
        {
            super.writePacketData(buf)
            buf.writeVarIntArray(timers)
            buf.writeVarInt(burnTime)
            buf.writeItemStack(container[0])
            buf.writeItemStack(container[1])
            buf.writeItemStack(container[2])
            buf.writeItemStack(container[3])
        }

        override fun readPacketData(buf: PacketBuffer)
        {
            super.readPacketData(buf)
            timers = buf.readVarIntArray()
            burnTime = buf.readInt()
            container[0] = buf.readItemStack()
            container[1] = buf.readItemStack()
            container[2] = buf.readItemStack()
            container[3] = buf.readItemStack()
        }
    }

    operator fun get(i : Int) = getStackInSlot(i)
    operator fun set(i : Int, stack : ItemStack) = setInventorySlotContents(i, stack)
}

