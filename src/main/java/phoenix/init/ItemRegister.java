package phoenix.init;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.client.models.entity.ModelMTH;
import phoenix.client.models.items.ModelMarkThree;
import phoenix.items.air.ArmorMarkThree;
import phoenix.items.air.ItemKassiysSpeaare;
import phoenix.items.redo.ItemMindDust;

import static phoenix.items.air.ArmorMarkThree.armorModels;

@GameRegistry.ObjectHolder(Phoenix.MOD_ID)
@Mod.EventBusSubscriber
public class ItemRegister
{

    @GameRegistry.ObjectHolder("absolute_swprd")   public static final Item ABS_SWORD = null;
    @GameRegistry.ObjectHolder("gods_boots")      public static final Item GODS_BOOTS = null;
    @GameRegistry.ObjectHolder("gods_chestplate") public static final Item GODS_CHESTPLATE = null;
    @GameRegistry.ObjectHolder("gods_leggins")    public static final Item GODS_LEGGINS = null;
    @GameRegistry.ObjectHolder("gods_halmet")     public static final Item GODS_HALMET = null;
    @GameRegistry.ObjectHolder("dust_mind")     public static final Item DUST_MIND = null;

    @SubscribeEvent
    public static void onRegistryItem(RegistryEvent.Register<Item> e)
    {
        ArmorMarkThree MarkHead = new ArmorMarkThree("gods_halmet", 1, EntityEquipmentSlot.HEAD),
                MarkLeggs = new ArmorMarkThree("gods_leggins", 2, EntityEquipmentSlot.LEGS),
                MarkChest = new ArmorMarkThree("gods_chestplate", 1, EntityEquipmentSlot.CHEST),
                MarkBoots = new  ArmorMarkThree("gods_boots", 1, EntityEquipmentSlot.FEET);
        e.getRegistry().registerAll
                (
                        MarkHead,
                        MarkLeggs,
                        MarkChest,
                        MarkBoots,
                        new ItemKassiysSpeaare(),
                        new ItemMindDust()
                );
        armorModels.put(MarkHead,  new ModelMTH());
        armorModels.put(MarkChest, new ModelMTH());
        armorModels.put(MarkLeggs, new ModelMTH());
        armorModels.put(MarkBoots, new ModelMTH());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegistryModel(ModelRegistryEvent e)
    {
        registryModel(ABS_SWORD);
        registryModel(GODS_BOOTS);
        registryModel(GODS_CHESTPLATE);
        registryModel(GODS_LEGGINS);
        registryModel(GODS_HALMET);
    }
    @SideOnly(Side.CLIENT)
    private static void registryModel(Item item) {
        final ResourceLocation regName = item.getRegistryName();// Не забываем, что getRegistryName может вернуть Null!
        final ModelResourceLocation mrl = new ModelResourceLocation(regName, "inventory");
        ModelBakery.registerItemVariants(item, mrl);// Регистрация вариантов предмета. Это нужно если мы хотим использовать подтипы предметов/блоков(см. статью подтипы)
        ModelLoader.setCustomModelResourceLocation(item, 0, mrl);// Устанавливаем вариант модели для нашего предмета. Без регистрации варианта модели, сама модель не будет установлена для предмета/блока(см. статью подтипы)
    }
}

