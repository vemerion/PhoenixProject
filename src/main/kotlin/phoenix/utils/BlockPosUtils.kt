package phoenix.utils

import net.minecraft.util.math.BlockPos

object BlockPosUtils
{
    fun distanceTo(first: BlockPos, second: BlockPos): Double
    {
        return Math.sqrt(((first.x - second.x) * (first.x - second.x) + (first.x - second.y) * (first.y - second.y) + (first.x - second.z) * (first.z - second.z)).toDouble())
    }

    fun isNear(pos: BlockPos, poses: Collection<BlockPos>, range: Int): Boolean
    {
        for (pos1 in poses) if (distanceTo(pos, pos1) < range) return false
        return true
    }
}