package phoenix.tile.ash

import net.minecraft.block.BlockState
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
import phoenix.blocks.ash.PotteryBarrelBlock.Companion.state
import phoenix.init.PhoenixTiles.POTTERY_BARREL
import phoenix.utils.block.PhoenixTile

class PotteryBarrelTile : PhoenixTile<PotteryBarrelTile>(POTTERY_BARREL), IInventory, ITickableTileEntity
{
    var jumpsCount = 0
    override fun tick()
    {
        if (!inventory.isEmpty)
        {
            if (inventory.item === Items.CLAY)
            {
                if (world!!.getBlockState(pos).get(state) == 1)
                {
                    world!!.setBlockState(pos, world!!.getBlockState(pos).with(state, 2))
                }
            } else if (inventory.item === Items.WATER_BUCKET)
            {
                if (world!!.getBlockState(pos).get(state) == 0)
                {
                    world!!.setBlockState(pos, world!!.getBlockState(pos).with(state, 1))
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

    override fun read(state: BlockState, compound: CompoundNBT)
    {
        super.read(state, compound)
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