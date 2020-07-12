package phoenix.init;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phoenix.Phoenix;
import phoenix.items.air.ArmorMarkThree;
import phoenix.items.air.ItemAbsoluteSpeedSword;
import phoenix.world.capablity.StageProvider;

@Mod.EventBusSubscriber
public class PhoenixEventHandler
{
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<World> event)
    {
        event.addCapability(new ResourceLocation(Phoenix.MOD_ID, Phoenix.MOD_NAME), new StageProvider());
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof EntityPlayer)
        {
            if (((EntityPlayer) event.getSource().getImmediateSource()).getHeldItemMainhand().getItem() instanceof ItemAbsoluteSpeedSword)
            {
                event.getEntity().hurtResistantTime = 0;
            }
        }
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            int C = 0;
            for (ItemStack stack: player.inventory.armorInventory) {
                if (stack.getItem() instanceof ArmorMarkThree) {
                    C++;
                }
                else
                {
                    return;
                }
            }
            if(C >= 4 && event.getAmount() > 0) {
                ItemStack stack = player.inventory.armorInventory.get(2);
                NBTTagCompound nbtCompoud = stack.getTagCompound();
                try {
                    if(nbtCompoud != null) {
                        int typed = nbtCompoud.getInteger(event.getSource().getDamageType().toLowerCase()),
                                all = nbtCompoud.getInteger("all_damage");
                        System.out.println(all + " " + typed);
                        event.setAmount(event.getAmount() * (Math.abs(((float) typed / (float) all))));
                        player.hurtResistantTime = 10;

                        nbtCompoud.setInteger(event.getSource().getDamageType().toLowerCase(), typed + 1);
                        nbtCompoud.setInteger("all_damage", all + 1);
                    }
                    else
                    {
                        stack.setTagCompound(new NBTTagCompound());
                        stack.getTagCompound().setInteger(event.getSource().getDamageType().toLowerCase(), 1);
                        stack.getTagCompound().setInteger("all_damage", 1);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
