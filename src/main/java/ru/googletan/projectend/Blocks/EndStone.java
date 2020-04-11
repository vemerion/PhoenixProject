package ru.googletan.projectend.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EndStone extends Block {
    public EndStone(Material blockMaterialIn, MapColor blockMapColorIn, String name) {
        super(blockMaterialIn, blockMapColorIn);
        setRegistryName(name);
        setHardness(3.0F);
        setResistance(15.0F);
        setSoundType(SoundType.STONE);
        setTranslationKey("whiteStone");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add("seeexy");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        Minecraft.getMinecraft().player.sendChatMessage("ee");
    }
    public EndStone(Material materialIn) {
        super(materialIn);
    }
}
