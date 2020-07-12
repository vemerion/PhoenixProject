package phoenix.blocks.rebirth;

import phoenix.Phoenix;
import phoenix.blocks.AbstractCeraomic;

public class BlockCeraomicPorcelain extends AbstractCeraomic
{
    public BlockCeraomicPorcelain()
    {
        super("ceramic_porcelain");
        setTranslationKey("ceraomic_porcelain");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        setHardness(3);
        setLightLevel(0.0F);
    }
}
