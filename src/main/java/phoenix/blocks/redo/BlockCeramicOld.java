package phoenix.blocks.redo;

import phoenix.Phoenix;
import phoenix.blocks.AbstractCeramic;

public class BlockCeramicOld extends AbstractCeramic
{
    public BlockCeramicOld()
    {
        super("ceramic");
        setHardness(300);
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }
}
