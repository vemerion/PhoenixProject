package phoenix.blocks.rebirth;

import phoenix.Phoenix;
import phoenix.blocks.AbstractCeramic;

public class BlockCeramicPorcelain extends AbstractCeramic
{
    public BlockCeramicPorcelain()
    {
        super("ceramic_porcelain");
        setTranslationKey("ceraomic_porcelain");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
        setHardness(3);
        setLightLevel(0.0F);
    }
}
