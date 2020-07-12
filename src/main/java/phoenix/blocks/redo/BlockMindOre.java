package phoenix.blocks.redo;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.init.ItemRegister;

import java.util.Random;

public class BlockMindOre extends BlockOre
{
    public BlockMindOre()
    {
        setRegistryName("mind_ore");
        setTranslationKey("mind_ore");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        setHardness(10);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemRegister.MIND_DUST;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(1000) > 800 ? 2 : 1;
    }
}
