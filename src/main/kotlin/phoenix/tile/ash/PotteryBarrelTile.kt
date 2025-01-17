package phoenix.tile.ash

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.ITickableTileEntity
import phoenix.blocks.ash.PotteryBarrelBlock.Companion.POTTERY_STATE
import phoenix.init.PhxTiles.potteryBarrel
import phoenix.utils.block.PhoenixTile
import phoenix.utils.get
import phoenix.utils.set

class PotteryBarrelTile : PhoenixTile(potteryBarrel), IInventory,
    ITickableTileEntity
{
    var jumpsCount = 0
    override fun tick()
    {
        val world = world
        if (world != null && !world.isRemote && !inventory.isEmpty)
        {
            if (inventory.item === Items.CLAY)
            {
                if (world[pos, POTTERY_STATE] == 1)
                {
                    world[pos, POTTERY_STATE] = 2
                }
            } else if (inventory.item === Items.WATER_BUCKET)
            {
                if (world[pos, POTTERY_STATE] == 0)
                {
                    world[pos, POTTERY_STATE] = 1
                }
            } else
            {
                InventoryHelper.spawnItemStack(
                    world,
                    pos.x.toDouble(),
                    (pos.y + 1).toDouble(),
                    pos.z.toDouble(),
                    inventory
                )
                inventory = ItemStack.EMPTY
            }
        }
    }

    override fun read(compound: CompoundNBT)
    {
        super.read(compound)
        jumpsCount = compound.getInt("jumpscount")
    }

    override fun write(compound: CompoundNBT): CompoundNBT
    {
        compound.putInt("jumpscount", jumpsCount)
        return super.write(compound)
    }

    fun incrementJumpsCount()
    {
        jumpsCount++
        jumpsCount = Math.min(jumpsCount, 200)
    }

    fun nullifyJumpsCount()
    {
        jumpsCount = 0
        removeStackFromSlot(0)
    }

    private var inventory = ItemStack.EMPTY
    override fun getUpdatePacket(): SUpdateTileEntityPacket
    {
        return UpdatePacket(jumpsCount)
    }

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket)
    {
        jumpsCount = (pkt as UpdatePacket).jumpsCount
    }

    override fun getSizeInventory(): Int
    {
        return 1
    }

    override fun isEmpty(): Boolean
    {
        return inventory.isEmpty
    }

    override fun getStackInSlot(index: Int): ItemStack
    {
        return inventory
    }

    override fun decrStackSize(index: Int, count: Int): ItemStack
    {
        val stack = inventory.copy()
        stack.shrink(count)
        return stack
    }

    override fun removeStackFromSlot(index: Int): ItemStack
    {
        return ItemStack.EMPTY.also { inventory = it }
    }

    override fun setInventorySlotContents(index: Int, stack: ItemStack)
    {
        inventory = stack
    }

    override fun isUsableByPlayer(player: PlayerEntity): Boolean
    {
        return true
    }

    override fun clear()
    {
        inventory = ItemStack.EMPTY
    }

    internal class UpdatePacket(var jumpsCount: Int) : SUpdateTileEntityPacket()
    {
        override fun readPacketData(buf: PacketBuffer)
        {
            super.readPacketData(buf)
            jumpsCount = buf.readInt()
        }

        override fun writePacketData(buf: PacketBuffer)
        {
            super.writePacketData(buf)
            buf.writeInt(jumpsCount)
        }
    }
}