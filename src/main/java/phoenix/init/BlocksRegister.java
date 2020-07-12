package phoenix.init;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.blocks.BlockGost;
import phoenix.blocks.BlockUpdater;
import phoenix.blocks.death.BlockJuiser;
import phoenix.blocks.death.BlockTank;
import phoenix.blocks.redo.BlockFarforCeraomic;
import phoenix.blocks.redo.BlockGoodEndStone;
import phoenix.blocks.redo.BlockKikiNBody;
import phoenix.blocks.redo.BlockKikiNFruit;
import phoenix.blocks.rebirth.BlockCeraomicOld;
import phoenix.blocks.rebirth.BlockMindOre;
import phoenix.fluid.blocks.BlockChorusJuise;
import phoenix.fluid.blocks.BlockKikinJuise;

@Mod.EventBusSubscriber(modid = Phoenix.MOD_ID)
public class BlocksRegister
{
    public static Block         MIND = new BlockMindOre();
    public static BlockGost     GOST = new BlockGost();
    public static BlockUpdater  UPDATER = new BlockUpdater();
    public static Block         FERTILE_END_STONE = new BlockGoodEndStone();
    public static Block         CERAOMC_OLD = new BlockCeraomicOld();
    public static Block         KIKIN_PLANT = new BlockKikiNBody();
    public static Block         KIKIN_FRUIT = new BlockKikiNFruit();
    public static BlockKikinJuise KIKIN_JUISE = new BlockKikinJuise(FluidRegister.KININ_FLUID);
    public static BlockChorusJuise CHORUS_JUISE = new BlockChorusJuise(FluidRegister.CHORUS_FLUID);
    public static Block CERAOMIC_FARFOR = new BlockFarforCeraomic();
    public static BlockTank TANK = new BlockTank();
    public static BlockJuiser JUISER = new BlockJuiser();
    //public static ModelResourceLocation gost_model;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll
        (
                MIND,
                GOST,
                UPDATER,
                FERTILE_END_STONE,
                CERAOMC_OLD,
                KIKIN_FRUIT,
                KIKIN_PLANT,
                KIKIN_JUISE,
                CHORUS_JUISE,
                TANK,
                CERAOMIC_FARFOR,
                JUISER
        );
        GameRegistry.registerTileEntity(UPDATER.getTileEntityClass(), UPDATER.getRegistryName().toString());
        GameRegistry.registerTileEntity(GOST.getTileEntityClass(),       GOST.getRegistryName().toString());
        GameRegistry.registerTileEntity(TANK.getTileEntityClass(),       TANK.getRegistryName().toString());
        GameRegistry.registerTileEntity(JUISER.getTileEntityClass(),   JUISER.getRegistryName().toString());
        //gost_model = new ModelResourceLocation(event.getRegistry().getKey(GOST), "normal");

        /*ModelLoader.setCustomStateMapper(GOST, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_){  return gost_model;  }
        });*/
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
        (
               new ItemBlock(MIND).setRegistryName(MIND.getRegistryName()),
               new ItemBlock(GOST).setRegistryName(GOST.getRegistryName()),
               new ItemBlock(UPDATER).setRegistryName(UPDATER.getRegistryName()),
               new ItemBlock(FERTILE_END_STONE).setRegistryName(FERTILE_END_STONE.getRegistryName()),
               new ItemBlock(CERAOMC_OLD).setRegistryName(CERAOMC_OLD.getRegistryName()),
               new ItemBlock(KIKIN_FRUIT).setRegistryName(KIKIN_FRUIT.getRegistryName()),
               new ItemBlock(KIKIN_PLANT).setRegistryName(KIKIN_PLANT.getRegistryName()),
               new ItemBlock(KIKIN_JUISE).setRegistryName(KIKIN_JUISE.getRegistryName()),
               new ItemBlock(CHORUS_JUISE).setRegistryName(CHORUS_JUISE.getRegistryName()),
               new ItemBlock(TANK).setRegistryName(TANK.getRegistryName()),
               new ItemBlock(CERAOMIC_FARFOR).setRegistryName(CERAOMIC_FARFOR.getRegistryName()),
               new ItemBlock(JUISER).setRegistryName(JUISER.getRegistryName())
        );
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(MIND);
        setRender(GOST);
        setRender(UPDATER);
        setRender(FERTILE_END_STONE);
        setRender(CERAOMC_OLD);
        setRender(KIKIN_FRUIT);
        setRender(KIKIN_PLANT);
        setRender(KIKIN_JUISE);
        setRender(CHORUS_JUISE);
        setRender(TANK);
        setRender(CERAOMIC_FARFOR);
        setRender(JUISER);
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    /*@SubscribeEvent model todo
    public static void onModelBakeEvent(ModelBakeEvent event)
    {
       TextureAtlasSprite crystalTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/crystal");
       IBakedModel customCrystalModel = new ModelMindOre(crystalTexture);
       event.getModelRegistry().putObject(gost_model, customCrystalModel);
    }*/
}
