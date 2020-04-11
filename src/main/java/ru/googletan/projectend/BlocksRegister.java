package ru.googletan.projectend;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.googletan.projectend.Blocks.EndStone;
import ru.googletan.projectend.Blocks.HelpBlock;
import ru.googletan.projectend.Blocks.SoriiuBlock;
import ru.googletan.projectend.Blocks.Unit01.Basor01;
import ru.googletan.projectend.Blocks.Unit01.GostBlock;
import ru.googletan.projectend.Blocks.Unit01.Updator01;
import ru.googletan.projectend.Blocks.title.Unit01.GostBlockTile;
import ru.googletan.projectend.Blocks.title.Unit01.Updator01TitleEntity;

public class BlocksRegister
{
    public static final Block
        UPDATOR01 =      new Updator01("updator01", Material.BARRIER, 10, 10, SoundType.GLASS).setCreativeTab(Projectend.TheEndOfCreativeTabs),
        BASOR =          new Basor01("basor").setCreativeTab(Projectend.TheEndOfCreativeTabs),
        GOST = new GostBlock("gost"),
        SORRY = new SoriiuBlock("psevdoair"),
        HELP = new HelpBlock("help"),
        END_STONE = new EndStone(Material.ROCK, MapColor.SAND, "end_stone");



    public static void register()
    {
        setRegister(UPDATOR01);
        setRegister(BASOR);
        setRegister(GOST);
        setRegister(SORRY);
        setRegister(HELP);
        GameRegistry.registerTileEntity(Updator01TitleEntity.class, UPDATOR01.getRegistryName().toString());
        GameRegistry.registerTileEntity(GostBlockTile.class, GOST.getRegistryName().toString());
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(UPDATOR01);
        setRender(BASOR);
        setRender(GOST);
        setRender(SORRY);
        setRender(HELP);
    }

    private static void setRegister(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
