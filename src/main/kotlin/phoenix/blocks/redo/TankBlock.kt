package phoenix.blocks.redo

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fluids.capability.IFluidHandler
import phoenix.Phoenix
import phoenix.tile.redo.TankTile
import phoenix.utils.block.BlockWithTile
import phoenix.utils.block.ICustomGroup


object TankBlock :
    BlockWithTile(Properties.create(Material.ROCK).setLightLevel { 5 }.notSolid()),
    ICustomGroup
{
    override fun onBlockActivated(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        player: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType
    {
        val tileTank = worldIn.getTileEntity(pos) as TankTile?
        if (player.getHeldItem(handIn).item === Items.BUCKET && tileTank!!.output.fluid.amount >= FluidAttributes.BUCKET_VOLUME)
        {
            val stack = tileTank.output.drain(1000, IFluidHandler.FluidAction.EXECUTE)
            player.getHeldItem(handIn).shrink(1)
            player.addItemStackToInventory(FluidUtil.getFilledBucket(stack))
            return ActionResultType.SUCCESS
        } else if (player.getHeldItem(handIn).item is BucketItem && tileTank!!.input.capacity - tileTank.input.fluidAmount >= FluidAttributes.BUCKET_VOLUME)
        {
            tileTank.input.fill(
                FluidUtil.getFluidContained(player.getHeldItem(handIn)).orElse(FluidStack.EMPTY),
                IFluidHandler.FluidAction.EXECUTE
            )
            player.getHeldItem(handIn).shrink(1)
            player.addItemStackToInventory(ItemStack(Items.BUCKET, 1))
            return ActionResultType.SUCCESS
        }
        return ActionResultType.CONSUME
    }

    override fun createTileEntity(state: BlockState, world: IBlockReader) = TankTile()

    override fun getTab() = Phoenix.REDO

    override fun getRenderType(state: BlockState) = BlockRenderType.ENTITYBLOCK_ANIMATED
}
