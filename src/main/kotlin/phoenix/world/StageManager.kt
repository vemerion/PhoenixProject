package phoenix.world

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.PaneBlock
import net.minecraft.client.audio.BackgroundMusicSelector
import net.minecraft.client.audio.BackgroundMusicTracks
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.IWorld
import net.minecraft.world.gen.feature.EndSpikeFeature
import net.minecraft.world.gen.feature.EndSpikeFeature.EndSpike
import phoenix.init.PhoenixBackgroundMusicTracks
import phoenix.init.PhoenixBlocks.ARMORED_GLASS

object StageManager
{
    private var data: CompoundNBT = CompoundNBT()

    fun read(nbt: CompoundNBT)
    {
        if (nbt.contains("stage_nbt"))
        {
            data = nbt.getCompound("stage_nbt")
        } else
        {
            val compound = CompoundNBT()
            compound.putInt("stage", 0)
            compound.putInt("part", 0)
            data = compound
            nbt.put("stage_nbt", compound)
        }
    }

    fun write(compound: CompoundNBT): CompoundNBT
    {
        compound.put("stage_nbt", data)
        return compound
    }

    var stage: Int
        get() = data.getInt("stage")
        set(stage) = data.putInt("stage", stage)
    val stageEnum: Stage
        get() = Stage.values()[data.getInt("stage") % Stage.values().size]
    var part: Int
        get() = data.getInt("part")
        set(part) = data.putInt("part", part)

    private fun setStage(stage: Int, provider: EndBiomeProvider)
    {
        data.putInt("stage", stage)
        GenSaveData.INSTANCE.markDirty()
        provider.updateLayer()
    }

    private fun addStage()
    {
        setStage((stage + 1).coerceAtMost(3), EndBiomeProvider.INSTANCE)
        GenSaveData.INSTANCE.markDirty()
    }

    fun addPart()
    {
        part += 1
        if (data.getInt("part") >= 3)
        {
            addStage()
            part = 0
        }
    }

    enum class Stage
    {
        ASH
        {
            override fun createTower(future: EndSpikeFeature, world: IWorld, spike: EndSpike)
            {
                if (spike.isGuarded)
                {
                    val pos = BlockPos.Mutable()
                    for (k in -2..2)
                    {
                        for (l in -2..2)
                        {
                            for (i1 in 0..3)
                            {
                                val isRight = MathHelper.abs(k) == 2
                                val ifLeft = MathHelper.abs(l) == 2
                                val isTop = i1 == 3
                                if (isRight || ifLeft || isTop)
                                {
                                    val isNorth = k == -2 || k == 2 || isTop
                                    val isEast = l == -2 || l == 2 || isTop
                                    val blockstate = Blocks.IRON_BARS.defaultState
                                        .with(PaneBlock.NORTH, isNorth && l != -2)
                                        .with(PaneBlock.SOUTH, isNorth && l != 2)
                                        .with(PaneBlock.WEST, isEast && k != -2)
                                        .with(PaneBlock.EAST, isEast && k != 2)
                                    world.setBlockState(pos.setPos(spike.centerX + k, spike.height + i1, spike.centerZ + l), blockstate, 3)
                                }
                            }
                        }
                    }
                }
            }
            override var backgroundSoundSelector: BackgroundMusicSelector = BackgroundMusicTracks.END_MUSIC
        },
        REDO
        {
            override fun createTower(future: EndSpikeFeature, world: IWorld, spike: EndSpike)
            {
                val pos = BlockPos.Mutable()
                for (k in -2..2)
                {
                    for (l in -2..2)
                    {
                        for (i1 in 0..3)
                        {
                            val isRight = MathHelper.abs(k) == 2
                            val ifLeft = MathHelper.abs(l) == 2
                            val isTop = i1 == 3
                            if (isRight || ifLeft || isTop)
                            {
                                val blockstate: BlockState = ARMORED_GLASS.defaultState
                                world.setBlockState(pos.setPos(spike.centerX + k, spike.height + i1, spike.centerZ + l), blockstate, 3)
                            }
                        }
                    }
                }
            }
            override var backgroundSoundSelector = PhoenixBackgroundMusicTracks.REDO_MUSIC
        },
        REBIRTH
        {
            override fun createTower(future: EndSpikeFeature, world: IWorld, spike: EndSpike)
            {
                ASH.createTower(future, world, spike)
            }
            override var backgroundSoundSelector = REDO.backgroundSoundSelector
        },
        AIR
        {
            override fun createTower(future: EndSpikeFeature, world: IWorld, spike: EndSpike)
            {
                ASH.createTower(future, world, spike)
            }

            override var backgroundSoundSelector = REBIRTH.backgroundSoundSelector
        };

        abstract fun createTower(future: EndSpikeFeature, world: IWorld, spike: EndSpike)
        abstract var backgroundSoundSelector : BackgroundMusicSelector
    }
}
