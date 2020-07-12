package phoenix;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PhoenixTab extends CreativeTabs
{
    public PhoenixTab(int index, String label)
    {
        super(index, label);
        this.createIcon();
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack(Item.getItemFromBlock(Blocks.END_PORTAL_FRAME));
    }
}
