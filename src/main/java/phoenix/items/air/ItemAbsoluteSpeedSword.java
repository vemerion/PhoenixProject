package phoenix.items.air;

import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import phoenix.Phoenix;

import javax.annotation.Nullable;
import java.util.List;

public class ItemAbsoluteSpeedSword extends ItemSword
{
    public ItemAbsoluteSpeedSword()
    {
        super(Phoenix.siliconlife);
        setRegistryName("absolute_speed_sword");
        setTranslationKey("absolutespeedsword");
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.translateToLocal("phoenix.massage.speare"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(EntityEquipmentSlot.MAINHAND);
            multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 96D, 0));
            return multimap;
        }
        return super.getItemAttributeModifiers(equipmentSlot);
    }
}
