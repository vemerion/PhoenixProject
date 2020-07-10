package phoenix.items.air;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import phoenix.Phoenix;

import java.util.HashMap;
import java.util.Map;

public class ArmorMarkThree extends ItemArmor
{

    public static final Map<Item, ModelBiped> armorModels = new HashMap<>();
    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("all_damage", 1);
        itemStack.setTagCompound(compound);
    }

    public ArmorMarkThree(String name, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
    {
        super(Phoenix.armorMaterial, renderIndexIn, equipmentSlotIn);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(Phoenix.TheEndOfCreativeTabs);
    }


    /*
    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
        ModelBiped armorModel = armorModels.get(this);

        if (armorModel != null)
        {
            armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
            armorModel.bipedHeadwear.showModel = false;
            armorModel.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS;
            armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
            armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
            armorModel.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
            armorModel.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;

            armorModel.isSneak = entityLiving.isSneaking();
            armorModel.isRiding = entityLiving.isRiding();
            armorModel.isChild = entityLiving.isChild();
        }
        return armorModel;
    }*/
}
