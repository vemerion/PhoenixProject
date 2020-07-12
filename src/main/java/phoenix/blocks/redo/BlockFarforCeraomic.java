package phoenix.blocks.redo;

import phoenix.Phoenix;
import phoenix.blocks.AbstractCeraomic;

public class BlockFarforCeraomic extends AbstractCeraomic
{
    public BlockFarforCeraomic()
    {
        super("ceraomic_china");
        setTranslationKey("ceraomic_china");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        setHardness(3);
        setLightLevel(0.0F);
    }
}
