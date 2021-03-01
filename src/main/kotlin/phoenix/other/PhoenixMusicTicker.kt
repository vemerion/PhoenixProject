package phoenix.other

import net.minecraft.client.audio.BackgroundMusicSelector
import net.minecraft.client.audio.BackgroundMusicTracks
import net.minecraft.client.audio.MusicTicker
import net.minecraft.util.math.MathHelper
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import phoenix.world.StageManager

@OnlyIn(Dist.CLIENT)
class PhoenixMusicTicker(old : MusicTicker) : MusicTicker(old.client)
{
    init
    {
        random = old.random
        client = old.client
        currentMusic = old.currentMusic
        timeUntilNextMusic = old.timeUntilNextMusic
    }

    override fun tick()
    {
        val selector: BackgroundMusicSelector = this.getBackgroundMusicSelector()

        if (this.currentMusic != null)
        {
            if (selector.soundEvent.name != this.currentMusic?.soundLocation && selector.shouldReplaceCurrentMusic())
            {
                this.client.getSoundHandler().stop(this.currentMusic)
                this.timeUntilNextMusic = MathHelper.nextInt(this.random, 0, selector.minDelay / 2)
            }
            if (!this.client.getSoundHandler().isPlaying(this.currentMusic))
            {
                this.currentMusic = null
                this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, MathHelper.nextInt(this.random, selector.minDelay, selector.maxDelay))
            }
        }
        this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, selector.maxDelay)
        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
        {
            this.selectRandomBackgroundMusic(selector)
        }
    }


    private fun getBackgroundMusicSelector() : BackgroundMusicSelector
    {
        val selector: BackgroundMusicSelector = client.backgroundMusicSelector
        return if(selector == BackgroundMusicTracks.END_MUSIC) StageManager.stageEnum.backgroundSoundSelector else selector
    }
}