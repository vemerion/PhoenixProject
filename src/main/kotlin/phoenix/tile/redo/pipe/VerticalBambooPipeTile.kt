package phoenix.tile.redo.pipe

import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import phoenix.api.block.IFluidPipe
import phoenix.init.PhxTiles
import phoenix.tile.FluidTileSidable
import phoenix.utils.MutablePair
import phoenix.utils.uniquePairOf

open class VerticalBambooPipeTile
    (
        type : TileEntityType<out BambooPipeTile> = PhxTiles.bambooPipe,
        capacity: Int = 1000,
        pullAmount: Int = 1000
    ) : FluidTileSidable(type, BlockStateProperties.FACING, pullAmount), ITickableTileEntity, IFluidPipe
{
    override var needSync: Boolean = false
        get() = false
        set(value) { field = false }

    override fun getDirections(): MutablePair<Direction?, Direction?> = uniquePairOf(blockState[facing], Direction.DOWN)
}
