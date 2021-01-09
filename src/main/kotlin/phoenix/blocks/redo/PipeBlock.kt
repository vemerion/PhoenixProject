package phoenix.blocks.redo

import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.IWaterLoggable
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemGroup
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import phoenix.Phoenix.REDO
import phoenix.tile.redo.PipeTile
import phoenix.utils.block.BlockWithTile
import phoenix.utils.block.ICustomGroup
import phoenix.utils.pipe.IFluidPipe


object PipeBlock : BlockWithTile(Properties.create(Material.BAMBOO).notSolid()), IWaterLoggable, ICustomGroup
{
    val UP : BooleanProperty
    val DOWN : BooleanProperty
    val NORTH : BooleanProperty
    val EAST : BooleanProperty
    val SOUTH : BooleanProperty
    val WEST : BooleanProperty
    val NORMAL: VoxelShape = makeCuboidShape(4.0, 4.0, 4.0, 12.0, 12.0, 12.0)

    init
    {
        UP = BooleanProperty.create("up")
        DOWN = BooleanProperty.create("down")
        NORTH = BooleanProperty.create("north")
        EAST = BooleanProperty.create("east")
        SOUTH = BooleanProperty.create("south")
        WEST = BooleanProperty.create("west")
        defaultState = stateContainer.baseState
            .with(NORTH, java.lang.Boolean.FALSE)
            .with(EAST, java.lang.Boolean.FALSE)
            .with(SOUTH, java.lang.Boolean.FALSE)
            .with(WEST, java.lang.Boolean.FALSE)
            .with(UP, java.lang.Boolean.FALSE)
            .with(DOWN, java.lang.Boolean.FALSE)
            .with(BlockStateProperties.WATERLOGGED, java.lang.Boolean.FALSE)
    }

    override fun onBlockActivated(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        player: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType
    {
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState?
    {
        val ifluidstate = context.world.getFluidState(context.pos)
        return makeConnections(context.world, context.pos).with(
            BlockStateProperties.WATERLOGGED,
            ifluidstate.fluid === Fluids.WATER
        )
    }

    fun makeConnections(reader: IBlockReader, pos: BlockPos): BlockState
    {
        val tile0 = reader.getTileEntity(pos.down())
        val tile1 = reader.getTileEntity(pos.up())
        val tile2 = reader.getTileEntity(pos.north())
        val tile3 = reader.getTileEntity(pos.east())
        val tile4 = reader.getTileEntity(pos.south())
        val tile5 = reader.getTileEntity(pos.west())
        return defaultState
            .with(DOWN, tile0 is IFluidPipe)
            .with(UP, tile1 is IFluidPipe)
            .with(NORTH, tile2 is IFluidPipe)
            .with(EAST, tile3 is IFluidPipe)
            .with(SOUTH, tile4 is IFluidPipe)
            .with(WEST, tile5 is IFluidPipe)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>)
    {
        try
        {
            builder.add(NORTH, EAST, WEST, SOUTH, UP, DOWN, BlockStateProperties.WATERLOGGED)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    override fun neighborChanged(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        blockIn: Block,
        fromPos: BlockPos,
        isMoving: Boolean
    )
    {
        val ifluidstate = worldIn.getFluidState(pos)
        worldIn.setBlockState(
            pos,
            makeConnections(worldIn, pos).with(BlockStateProperties.WATERLOGGED, ifluidstate.fluid === Fluids.WATER)
        )
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving)
    }

    override fun getFluidState(state: BlockState): FluidState
    {
        return if (state.get(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getStillFluidState(false) else super.getFluidState(
            state
        )
    }

    override fun getRenderType(state: BlockState): BlockRenderType
    {
        return BlockRenderType.INVISIBLE
    }

    override fun createTileEntity(state: BlockState, world: IBlockReader): TileEntity?
    {
        return PipeTile()
    }

    override fun getShape(
        state: BlockState,
        worldIn: IBlockReader,
        pos: BlockPos,
        context: ISelectionContext
    ): VoxelShape
    {
        return NORMAL
    }

    override fun getTab(): ItemGroup
    {
        return REDO
    }

}
