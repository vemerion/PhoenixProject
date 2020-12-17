package phoenix.mixin;

import kotlin.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoenix.utils.capablity.Date;
import phoenix.utils.capablity.IChapterReader;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public class MixinEntityPlayer implements IChapterReader
{
    @Inject(method = "writeAdditional", at = @At("TAIL"))
    private void onWriteEntityToNBT(CompoundNBT nbt, CallbackInfo ci)
    {
        nbt.putInt("count", chapters.size());
        for (int i = 0; i < chapters.size(); i++)
        {
            nbt.putInt("chid" + i, chapters.get(i).getFirst());
            nbt.putInt("chmin" + i, chapters.get(i).getSecond().getMinute());
            nbt.putInt("chday" + i, chapters.get(i).getSecond().getDay());
            nbt.putInt("chyear" + i, chapters.get(i).getSecond().getYear());
        }
    }

    @Inject(method = "readAdditional", at = @At("TAIL"))
    private void onReadEntityFromNBT(CompoundNBT nbt, CallbackInfo ci)
    {
        int count = nbt.getInt("count");
        for (int i = 0; i < count; ++i)
        {
            int id   = nbt.getInt("chid$i");
            int min  = nbt.getInt("chmin$i");
            int day  = nbt.getInt("chday$i");
            int year = nbt.getInt("chyear$i");
            addChapter(id, new Date(min, day, year));
        }
    }

    public ArrayList<Pair<Integer, Date>> chapters = new ArrayList<>();
    public boolean addChapter(int id, Date date) { return chapters.add(new Pair<>(id, date)); }
    public List<Pair<Integer, Date>> getOpenedChapters() { return chapters; }
}