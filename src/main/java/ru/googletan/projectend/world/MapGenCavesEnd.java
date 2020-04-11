package ru.googletan.projectend.world;

import com.google.common.base.MoreObjects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import java.util.Random;

public class MapGenCavesEnd extends MapGenBase
{
    protected static final IBlockState BLK_LAVA;
    protected static final IBlockState BLK_AIR;
    protected static final IBlockState BLK_SANDSTONE;
    protected static final IBlockState BLK_RED_SANDSTONE;

    public MapGenCavesEnd() {
    }

    protected void addRoom(long p_addRoom_1_, int p_addRoom_3_, int p_addRoom_4_, ChunkPrimer p_addRoom_5_, double p_addRoom_6_, double p_addRoom_8_, double p_addRoom_10_) {
        this.addTunnel(p_addRoom_1_, p_addRoom_3_, p_addRoom_4_, p_addRoom_5_, p_addRoom_6_, p_addRoom_8_, p_addRoom_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void addTunnel(long rand, int p_addTunnel_3_, int p_addTunnel_4_, ChunkPrimer p_addTunnel_5_, double p_addTunnel_6_, double p_addTunnel_8_, double p_addTunnel_10_, float p_addTunnel_12_, float p_addTunnel_13_, float p_addTunnel_14_, int p_addTunnel_15_, int p_addTunnel_16_, double p_addTunnel_17_) {
        double d0 = p_addTunnel_3_ * 16 + 8;
        double d1 = p_addTunnel_4_ * 16 + 8;
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(rand);
        if (p_addTunnel_16_ <= 0) {
            int i = this.range * 16 - 16;
            p_addTunnel_16_ = i - random.nextInt(i / 4);
        }

        boolean flag2 = false;
        if (p_addTunnel_15_ == -1) {
            p_addTunnel_15_ = p_addTunnel_16_ / 2;
            flag2 = true;
        }

        int j = random.nextInt(p_addTunnel_16_ / 2) + p_addTunnel_16_ / 4;

        for(boolean flag = random.nextInt(6) == 0; p_addTunnel_15_ < p_addTunnel_16_; ++p_addTunnel_15_) {
            double d2 = 1.5D + (double)(MathHelper.sin((float)p_addTunnel_15_ * 3.1415927F / (float)p_addTunnel_16_) * p_addTunnel_12_);
            double d3 = d2 * p_addTunnel_17_;
            float f2 = MathHelper.cos(p_addTunnel_14_);
            float f3 = MathHelper.sin(p_addTunnel_14_);
            p_addTunnel_6_ += (double)(MathHelper.cos(p_addTunnel_13_) * f2);
            p_addTunnel_8_ += (double)f3;
            p_addTunnel_10_ += (double)(MathHelper.sin(p_addTunnel_13_) * f2);
            if (flag) {
                p_addTunnel_14_ *= 0.92F;
            } else {
                p_addTunnel_14_ *= 0.7F;
            }

            p_addTunnel_14_ += f1 * 0.1F;
            p_addTunnel_13_ += f * 0.1F;
            f1 *= 0.9F;
            f *= 0.75F;
            f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (!flag2 && p_addTunnel_15_ == j && p_addTunnel_12_ > 1.0F && p_addTunnel_16_ > 0) {
                this.addTunnel(random.nextLong(), p_addTunnel_3_, p_addTunnel_4_, p_addTunnel_5_, p_addTunnel_6_, p_addTunnel_8_, p_addTunnel_10_, random.nextFloat() * 0.5F + 0.5F, p_addTunnel_13_ - 1.5707964F, p_addTunnel_14_ / 3.0F, p_addTunnel_15_, p_addTunnel_16_, 1.0D);
                this.addTunnel(random.nextLong(), p_addTunnel_3_, p_addTunnel_4_, p_addTunnel_5_, p_addTunnel_6_, p_addTunnel_8_, p_addTunnel_10_, random.nextFloat() * 0.5F + 0.5F, p_addTunnel_13_ + 1.5707964F, p_addTunnel_14_ / 3.0F, p_addTunnel_15_, p_addTunnel_16_, 1.0D);
                return;
            }

            if (flag2 || random.nextInt(4) != 0) {
                double d4 = p_addTunnel_6_ - d0;
                double d5 = p_addTunnel_10_ - d1;
                double d6 = (double)(p_addTunnel_16_ - p_addTunnel_15_);
                double d7 = (double)(p_addTunnel_12_ + 2.0F + 16.0F);
                if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
                    return;
                }

                if (p_addTunnel_6_ >= d0 - 16.0D - d2 * 2.0D && p_addTunnel_10_ >= d1 - 16.0D - d2 * 2.0D && p_addTunnel_6_ <= d0 + 16.0D + d2 * 2.0D && p_addTunnel_10_ <= d1 + 16.0D + d2 * 2.0D) {
                    int k2 = MathHelper.floor(p_addTunnel_6_ - d2) - p_addTunnel_3_ * 16 - 1;
                    int k = MathHelper.floor(p_addTunnel_6_ + d2) - p_addTunnel_3_ * 16 + 1;
                    int l2 = MathHelper.floor(p_addTunnel_8_ - d3) - 1;
                    int l = MathHelper.floor(p_addTunnel_8_ + d3) + 1;
                    int i3 = MathHelper.floor(p_addTunnel_10_ - d2) - p_addTunnel_4_ * 16 - 1;
                    int i1 = MathHelper.floor(p_addTunnel_10_ + d2) - p_addTunnel_4_ * 16 + 1;
                    if (k2 < 0) {
                        k2 = 0;
                    }

                    if (k > 16) {
                        k = 16;
                    }

                    if (l2 < 1) {
                        l2 = 1;
                    }

                    if (l > 248) {
                        l = 248;
                    }

                    if (i3 < 0) {
                        i3 = 0;
                    }

                    if (i1 > 16) {
                        i1 = 16;
                    }

                    boolean flag3 = false;

                    int j3;
                    for(int j1 = k2; !flag3 && j1 < k; ++j1) {
                        for(j3 = i3; !flag3 && j3 < i1; ++j3) {
                            for(int l1 = l + 1; !flag3 && l1 >= l2 - 1; --l1) {
                                if (l1 >= 0 && l1 < 256) {
                                    if (this.isOceanBlock(p_addTunnel_5_, j1, l1, j3, p_addTunnel_3_, p_addTunnel_4_)) {
                                        flag3 = true;
                                    }

                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && j3 != i3 && j3 != i1 - 1) {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag3) {
                        new BlockPos.MutableBlockPos();

                        for(j3 = k2; j3 < k; ++j3) {
                            double d10 = ((double)(j3 + p_addTunnel_3_ * 16) + 0.5D - p_addTunnel_6_) / d2;

                            for(int i2 = i3; i2 < i1; ++i2) {
                                double d8 = ((double)(i2 + p_addTunnel_4_ * 16) + 0.5D - p_addTunnel_10_) / d2;
                                boolean flag1 = false;
                                if (d10 * d10 + d8 * d8 < 1.0D) {
                                    for(int j2 = l; j2 > l2; --j2) {
                                        double d9 = ((double)(j2 - 1) + 0.5D - p_addTunnel_8_) / d3;
                                        if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
                                            IBlockState iblockstate1 = p_addTunnel_5_.getBlockState(j3, j2, i2);
                                            IBlockState iblockstate2 = (IBlockState)MoreObjects.firstNonNull(p_addTunnel_5_.getBlockState(j3, j2 + 1, i2), BLK_AIR);
                                            if (this.isTopBlock(p_addTunnel_5_, j3, j2, i2, p_addTunnel_3_, p_addTunnel_4_)) {
                                                flag1 = true;
                                            }

                                            this.digBlock(p_addTunnel_5_, j3, j2, i2, p_addTunnel_3_, p_addTunnel_4_, flag1, iblockstate1, iblockstate2);
                                        }
                                    }
                                }
                            }
                        }

                        if (flag2) {
                            break;
                        }
                    }
                }
            }
        }

    }

    protected boolean canReplaceBlock(IBlockState p_canReplaceBlock_1_, IBlockState p_canReplaceBlock_2_) {
        if (p_canReplaceBlock_1_.getBlock() == Blocks.STONE) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.DIRT) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.GRASS) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.HARDENED_CLAY) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.SANDSTONE) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.RED_SANDSTONE) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.MYCELIUM) {
            return true;
        } else if (p_canReplaceBlock_1_.getBlock() == Blocks.SNOW_LAYER) {
            return true;
        } else {
            return (p_canReplaceBlock_1_.getBlock() == Blocks.SAND || p_canReplaceBlock_1_.getBlock() == Blocks.GRAVEL) && p_canReplaceBlock_2_.getMaterial() != Material.WATER;
        }
    }

    protected void recursiveGenerate(World p_recursiveGenerate_1_, int p_recursiveGenerate_2_, int p_recursiveGenerate_3_, int p_recursiveGenerate_4_, int p_recursiveGenerate_5_, ChunkPrimer p_recursiveGenerate_6_) {
        int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
        if (this.rand.nextInt(7) != 0) {
            i = 0;
        }

        for(int j = 0; j < i; ++j) {
            double d0 = (double)(p_recursiveGenerate_2_ * 16 + this.rand.nextInt(16));
            double d1 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
            double d2 = (double)(p_recursiveGenerate_3_ * 16 + this.rand.nextInt(16));
            int k = 1;
            if (this.rand.nextInt(4) == 0) {
                this.addRoom(this.rand.nextLong(), p_recursiveGenerate_4_, p_recursiveGenerate_5_, p_recursiveGenerate_6_, d0, d1, d2);
                k += this.rand.nextInt(4);
            }

            for(int l = 0; l < k; ++l) {
                float f = this.rand.nextFloat() * 6.2831855F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
                if (this.rand.nextInt(10) == 0) {
                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.addTunnel(this.rand.nextLong(), p_recursiveGenerate_4_, p_recursiveGenerate_5_, p_recursiveGenerate_6_, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }
        }

    }

    protected boolean isOceanBlock(ChunkPrimer p_isOceanBlock_1_, int p_isOceanBlock_2_, int p_isOceanBlock_3_, int p_isOceanBlock_4_, int p_isOceanBlock_5_, int p_isOceanBlock_6_) {
        Block block = p_isOceanBlock_1_.getBlockState(p_isOceanBlock_2_, p_isOceanBlock_3_, p_isOceanBlock_4_).getBlock();
        return block == Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    private boolean isExceptionBiome(Biome p_isExceptionBiome_1_) {
        if (p_isExceptionBiome_1_ == Biomes.BEACH) {
            return true;
        } else {
            return p_isExceptionBiome_1_ == Biomes.DESERT;
        }
    }

    private boolean isTopBlock(ChunkPrimer p_isTopBlock_1_, int p_isTopBlock_2_, int p_isTopBlock_3_, int p_isTopBlock_4_, int p_isTopBlock_5_, int p_isTopBlock_6_) {
        Biome biome = this.world.getBiome(new BlockPos(p_isTopBlock_2_ + p_isTopBlock_5_ * 16, 0, p_isTopBlock_4_ + p_isTopBlock_6_ * 16));
        IBlockState state = p_isTopBlock_1_.getBlockState(p_isTopBlock_2_, p_isTopBlock_3_, p_isTopBlock_4_);
        return this.isExceptionBiome(biome) ? state.getBlock() == Blocks.GRASS : state.getBlock() == biome.topBlock;
    }

    protected void digBlock(ChunkPrimer p_digBlock_1_, int p_digBlock_2_, int p_digBlock_3_, int p_digBlock_4_, int p_digBlock_5_, int p_digBlock_6_, boolean p_digBlock_7_, IBlockState p_digBlock_8_, IBlockState p_digBlock_9_) {
        Biome biome = this.world.getBiome(new BlockPos(p_digBlock_2_ + p_digBlock_5_ * 16, 0, p_digBlock_4_ + p_digBlock_6_ * 16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;
        if (this.canReplaceBlock(p_digBlock_8_, p_digBlock_9_) || p_digBlock_8_.getBlock() == top.getBlock() || p_digBlock_8_.getBlock() == filler.getBlock()) {
            if (p_digBlock_3_ - 1 < 10) {
                p_digBlock_1_.setBlockState(p_digBlock_2_, p_digBlock_3_, p_digBlock_4_, BLK_LAVA);
            } else {
                p_digBlock_1_.setBlockState(p_digBlock_2_, p_digBlock_3_, p_digBlock_4_, BLK_AIR);
                if (p_digBlock_7_ && p_digBlock_1_.getBlockState(p_digBlock_2_, p_digBlock_3_ - 1, p_digBlock_4_).getBlock() == filler.getBlock()) {
                    p_digBlock_1_.setBlockState(p_digBlock_2_, p_digBlock_3_ - 1, p_digBlock_4_, top.getBlock().getDefaultState());
                }
            }
        }

    }

    static {
        BLK_LAVA = Blocks.LAVA.getDefaultState();
        BLK_AIR = Blocks.AIR.getDefaultState();
        BLK_SANDSTONE = Blocks.SANDSTONE.getDefaultState();
        BLK_RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
    }
}
