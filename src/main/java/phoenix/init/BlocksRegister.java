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
import phoenix.blocks.FragileBlock;
import phoenix.blocks.BlockUpdater;
import phoenix.blocks.death.BlockJuicer;
import phoenix.blocks.death.BlockTank;
import phoenix.blocks.rebirth.BlockCeramicPorcelain;
import phoenix.blocks.rebirth.BlockGoodEndStone;
import phoenix.blocks.rebirth.BlockKikinStem;
import phoenix.blocks.rebirth.BlockKikinFruit;
import phoenix.blocks.redo.BlockCeramicOld;
import phoenix.blocks.redo.BlockMindOre;
import phoenix.fluid.blocks.BlockChorusJuice;
import phoenix.fluid.blocks.BlockKikinJuice;

@Mod.EventBusSubscriber(modid = Phoenix.MOD_ID)
public class BlocksRegister
{
    public static Block MIND = new BlockMindOre();
    public static FragileBlock FRAGILE_BLOCK = new FragileBlock();
    public static BlockUpdater UPDATER = new BlockUpdater();
    public static Block FERTILE_END_STONE = new BlockGoodEndStone();
    public static Block CERAMIC_OLD = new BlockCeramicOld();
    public static Block KIKIN_PLANT = new BlockKikinStem();
    public static Block KIKIN_FRUIT = new BlockKikinFruit();
    public static BlockKikinJuice KIKIN_JUICE = new BlockKikinJuice(FluidRegister.KIKIN_FLUID);
    public static BlockChorusJuice CHORUS_JUICE = new BlockChorusJuice(FluidRegister.CHORUS_FLUID);
    public static Block CERAMIC_PORCELAIN = new BlockCeramicPorcelain();
    public static BlockTank TANK = new BlockTank();
    public static BlockJuicer JUICER = new BlockJuicer();
    //public static ModelResourceLocation fragileblock_model;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll
        (
                MIND,
                FRAGILE_BLOCK,
                UPDATER,
                FERTILE_END_STONE,
                CERAMIC_OLD,
                KIKIN_FRUIT,
                KIKIN_PLANT,
                KIKIN_JUICE,
                CHORUS_JUICE,
                TANK,
                CERAMIC_PORCELAIN,
                JUICER
        );
        GameRegistry.registerTileEntity(UPDATER.getTileEntityClass(), UPDATER.getRegistryName().toString());
        GameRegistry.registerTileEntity(FRAGILE_BLOCK.getTileEntityClass(), FRAGILE_BLOCK.getRegistryName().toString());
        GameRegistry.registerTileEntity(TANK.getTileEntityClass(), TANK.getRegistryName().toString());
        GameRegistry.registerTileEntity(JUICER.getTileEntityClass(), JUICER.getRegistryName().toString());
        //ghost_model = new ModelResourceLocation(event.getRegistry().getKey(FRAGILE_BLOCK), "normal");

        /*ModelLoader.setCustomStateMapper(FRAGILE_BLOCK, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_){  return fragileblock_model;  }
        });*/
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
        (
               new ItemBlock(MIND).setRegistryName(MIND.getRegistryName()),
               new ItemBlock(FRAGILE_BLOCK).setRegistryName(FRAGILE_BLOCK.getRegistryName()),
               new ItemBlock(UPDATER).setRegistryName(UPDATER.getRegistryName()),
               new ItemBlock(FERTILE_END_STONE).setRegistryName(FERTILE_END_STONE.getRegistryName()),
               new ItemBlock(CERAMIC_OLD).setRegistryName(CERAMIC_OLD.getRegistryName()),
               new ItemBlock(KIKIN_FRUIT).setRegistryName(KIKIN_FRUIT.getRegistryName()),
               new ItemBlock(KIKIN_PLANT).setRegistryName(KIKIN_PLANT.getRegistryName()),
               new ItemBlock(KIKIN_JUICE).setRegistryName(KIKIN_JUICE.getRegistryName()),
               new ItemBlock(CHORUS_JUICE).setRegistryName(CHORUS_JUICE.getRegistryName()),
               new ItemBlock(TANK).setRegistryName(TANK.getRegistryName()),
               new ItemBlock(CERAMIC_PORCELAIN).setRegistryName(CERAMIC_PORCELAIN.getRegistryName()),
               new ItemBlock(JUICER).setRegistryName(JUICER.getRegistryName())
        );
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(MIND);
        setRender(FRAGILE_BLOCK);
        setRender(UPDATER);
        setRender(FERTILE_END_STONE);
        setRender(CERAMIC_OLD);
        setRender(KIKIN_FRUIT);
        setRender(KIKIN_PLANT);
        setRender(KIKIN_JUICE);
        setRender(CHORUS_JUICE);
        setRender(TANK);
        setRender(CERAMIC_PORCELAIN);
        setRender(JUICER);
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
       event.getModelRegistry().putObject(ghost_model, customCrystalModel);
    }*/
}
