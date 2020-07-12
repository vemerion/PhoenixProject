package phoenix.blocks.redo;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import phoenix.Phoenix;
import phoenix.blocks.PhoenixBlock;

public class BlockGoodEndStone extends PhoenixBlock
{
    public BlockGoodEndStone()
    {
        super(Material.ROCK);
        setRegistryName("fertile_end_stone");
        setTranslationKey("fertile_end_stone");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }
}
