package phoenix.blocks.rebirth;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import phoenix.Phoenix;

public class BlockGoodEndStone extends Block
{
    public BlockGoodEndStone()
    {
        super(Material.ROCK);
        setRegistryName("fertile_end_stone");
        setTranslationKey("fertile_end_stone");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }
}
