package phoenix.world

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.PaneBlock
import net.minecraft.client.audio.BackgroundMusicSelector
import net.minecraft.client.audio.BackgroundMusicTracks
import net.minecraft.client.resources.I18n
import net.minecraft.entity.EntityType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.IServerWorld
import net.minecraft.world.gen.feature.EndSpikeFeature
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig
import net.minecraftforge.client.event.RenderGameOverlayEvent
import phoenix.init.PhoenixBackgroundMusicTracks
import phoenix.init.PhoenixBlocks.ARMORED_GLASS
import java.util.*
import kotlin.math.min

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
        set(stage) {
            data.putInt("stage", stage)
            EndBiomeProvider.INSTANCE.updateLayer()
            GenSaveData.INSTANCE.markDirty()
        }

    var part: Int
        get() = data.getInt("part")
        set(part) {
            data.putInt("part", part)
            if (data.getInt("part") >= 3)
            {
                stage++
                this.part = 0
            }
            GenSaveData.INSTANCE.markDirty()
        }

    val stageEnum: Stage
        get() = Stage.values()[min(data.getInt("stage"), Stage.values().size - 1)]

    enum class Stage
    {
        ASH
        {
            override fun createTower(world : IServerWorld, rand : Random, config : EndSpikeFeatureConfig, spike : EndSpikeFeature.EndSpike)
            {
                val i = spike.radius

                for (pos in BlockPos.getAllInBoxMutable(BlockPos(spike.centerX - i, 0, spike.centerZ - i), BlockPos(spike.centerX + i, spike.height + 10, spike.centerZ + i)))
                {
                    if (pos.distanceSq(spike.centerX.toDouble(), pos.y.toDouble(), spike.centerZ.toDouble(), false) <= (i * i + 1).toDouble()
                                        && pos.y < spike.height)
                    {
                        world.setBlockState(pos, Blocks.OBSIDIAN.defaultState, 2)
                    }
                    else if (pos.y > 65)
                    {
                        world.setBlockState(pos, Blocks.AIR.defaultState, 2)
                    }
                }

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
                                    val toPlace = Blocks.IRON_BARS.defaultState
                                        .with(PaneBlock.NORTH, isNorth && l != -2)
                                        .with(PaneBlock.SOUTH, isNorth && l != 2)
                                        .with(PaneBlock.WEST, isEast && k != -2)
                                        .with(PaneBlock.EAST, isEast && k != 2)
                                    world.setBlockState(pos.setPos(spike.centerX + k, spike.height + i1, spike.centerZ + l), toPlace, 3)
                                }
                            }
                        }
                    }
                }

                val entity = EntityType.END_CRYSTAL.create(world.world)
                if(entity != null)
                {
                    entity.beamTarget = config.crystalBeamTarget
                    entity.isInvulnerable = config.isCrystalInvulnerable
                    entity.setLocationAndAngles(spike.centerX.toDouble() + 0.5, (spike.height + 1).toDouble(), spike.centerZ.toDouble() + 0.5, rand.nextFloat() * 360.0f, 0.0f)
                    world.addEntity(entity)
                    world.setBlockState(BlockPos(spike.centerX, spike.height, spike.centerZ), Blocks.BEDROCK.defaultState, 2)
                }
            }
            override var backgroundSoundSelector: BackgroundMusicSelector = BackgroundMusicTracks.END_MUSIC
            override val inguiName: ITextComponent
                get() = StringTextComponent(I18n.format("stage.ash"))
        },
        REDO
        {
            override fun createTower(world : IServerWorld, rand : Random, config : EndSpikeFeatureConfig, spike : EndSpikeFeature.EndSpike)
            {
                val i = spike.radius

                for (blockpos in BlockPos.getAllInBoxMutable(
                    BlockPos(spike.centerX - i, 0, spike.centerZ - i),
                    BlockPos(spike.centerX + i, spike.height + 10, spike.centerZ + i)
                ))
                {
                    if (blockpos.distanceSq(
                            spike.centerX.toDouble(),
                            blockpos.y.toDouble(), spike.centerZ.toDouble(), false
                        ) <= (i * i + 1).toDouble() && blockpos.y < spike.height
                    )
                    {
                        world.setBlockState(blockpos, Blocks.OBSIDIAN.defaultState, 2)
                    } else if (blockpos.y > 65)
                    {
                        world.setBlockState(blockpos, Blocks.AIR.defaultState, 2)
                    }
                }

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
                                world.setBlockState(pos.setPos(spike.centerX + k, spike.height + i1, spike.centerZ + l), ARMORED_GLASS.defaultState, 3)
                            }
                        }
                    }
                }

                val entity = EntityType.END_CRYSTAL.create(world.world)
                if(entity != null)
                {
                    entity.beamTarget = config.crystalBeamTarget
                    entity.isInvulnerable = config.isCrystalInvulnerable
                    entity.setLocationAndAngles(spike.centerX.toDouble() + 0.5, (spike.height + 1).toDouble(), spike.centerZ.toDouble() + 0.5, rand.nextFloat() * 360.0f, 0.0f)
                    world.addEntity(entity)
                    world.setBlockState(BlockPos(spike.centerX, spike.height, spike.centerZ), Blocks.BEDROCK.defaultState, 2)
                }

            }
            override var backgroundSoundSelector = PhoenixBackgroundMusicTracks.REDO_MUSIC
            override val inguiName: ITextComponent
                get() = StringTextComponent(I18n.format("stage.redo"))
        },
        REBIRTH
        {
            override fun createTower(worldIn : IServerWorld, rand : Random, config : EndSpikeFeatureConfig, spike : EndSpikeFeature.EndSpike)
            {
                ASH.createTower(worldIn, rand, config, spike)
            }
            override var backgroundSoundSelector = REDO.backgroundSoundSelector
            override val inguiName: ITextComponent
                get() = StringTextComponent(I18n.format("stage.rebirth"))
        },
        AIR
        {
            override fun createTower(worldIn : IServerWorld, rand : Random, config : EndSpikeFeatureConfig, spike : EndSpikeFeature.EndSpike)
            {
                ASH.createTower(worldIn, rand, config, spike)
            }

            override var backgroundSoundSelector = REBIRTH.backgroundSoundSelector
            override val inguiName: ITextComponent
                get() = StringTextComponent(I18n.format("stage.air"))
        };

        abstract fun createTower(worldIn : IServerWorld, rand : Random, config : EndSpikeFeatureConfig, spike : EndSpikeFeature.EndSpike)
        abstract var backgroundSoundSelector : BackgroundMusicSelector
        abstract val inguiName : ITextComponent
    }
}
