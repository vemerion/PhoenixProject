package phoenix.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import phoenix.client.sound.CeramicSoundType;

import javax.annotation.Nullable;

public abstract class AbstractCeraomic extends PhoenixBlock
{
    public AbstractCeraomic(String name)
    {
        super(Material.ROCK);
        setRegistryName(name);
        setTranslationKey(name);
    }

    @Override public boolean canDropFromExplosion(Explosion explosionIn) { return false; }
    @Override public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) { return 20; }
    @Override public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {   return true;  }
    @Override public boolean isBurning(IBlockAccess world, BlockPos pos) { return false; }
    @Override public SoundType getSoundType() { return CeramicSoundType.TEST;  }
}
