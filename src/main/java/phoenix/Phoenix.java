package phoenix;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phoenix.init.Common;
import phoenix.world.capablity.IStager;

@Mod
(
   modid = Phoenix.MOD_ID,
    name = Phoenix.MOD_NAME,
 version = Phoenix.VERSION
)
public class Phoenix
{
    public static final String MOD_ID = "phoenix";
    public static final String MOD_NAME = "Project Phoenix";
    public static final String VERSION = "0.0.4B";
    public static final CreativeTabs            TheEndOfCreativeTabs = new PhoenixTab(CreativeTabs.getNextID(), "end_of_the_tabs");
    public static final Item.ToolMaterial       siliconlife          = EnumHelper.addToolMaterial ("phoenix:silinonlife", 2, 9000, 0.0F, 1.0F, 12);
    public static final ItemArmor.ArmorMaterial armorMaterial        = EnumHelper.addArmorMaterial("phoenix:lifedarmor", "phoenix:lifedarmor", 9000, new int[]{4, 7, 5, 4}, 10, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 1.0F);
    static {     FluidRegistry.enableUniversalBucket();   }

    @CapabilityInject(IStager.class)
    public static final Capability<IStager> STAGER_CAPABILITY = null;

    @Mod.Instance(MOD_ID)
    public static Phoenix INSTANCE;

    public Phoenix() { INSTANCE = this; }

    public static Logger getLogger() {  return LogManager.getLogger();  }

    @SidedProxy(clientSide = "phoenix.init.Client", serverSide = "phoenix.init.Common") public static Common proxy;
    @Mod.EventHandler  public void preInit(FMLPreInitializationEvent event)  { proxy.preInit(event);  }
    @Mod.EventHandler  public void init(FMLInitializationEvent event)        { proxy.init(event);     }
    @Mod.EventHandler  public void postInit(FMLPostInitializationEvent event){ proxy.postInit(event); }
}
