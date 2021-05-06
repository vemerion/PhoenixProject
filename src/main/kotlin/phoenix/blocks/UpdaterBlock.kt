package phoenix.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.Material
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import phoenix.init.PhoenixSounds
import phoenix.init.PhoenixSounds.CHANGE_STAGE
import phoenix.world.EndBiomeProvider
import phoenix.world.StageManager

object UpdaterBlock : Block(Properties.create(Material.ROCK).setLightLevel { 5 }.hardnessAndResistance(-1f))
{
    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, placer: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult) : ActionResultType
    {
        if (!worldIn.isRemote)
        {
            worldIn.playSound(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), CHANGE_STAGE, SoundCategory.BLOCKS, 1f, 1f, true)
            StageManager.part++
            for (entity in worldIn.players)
            {
                entity.sendStatusMessage(StringTextComponent(I18n.format("phoenix.message.newstage")), false)
                entity.sendStatusMessage(StringTextComponent("${StageManager.stage + 1} ${StageManager.part + 1}"), false)
            }
            worldIn.setBlockState(pos, Blocks.AIR.defaultState)
            worldIn.playSound(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), CHANGE_STAGE, SoundCategory.BLOCKS, 1.0f, 1.0f, true)
        }
        return super.onBlockActivated(state, worldIn, pos, placer, handIn, hit)
    }
}