package phoenix.init.events

import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.data.BlockTagsProvider
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.potion.*
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.server.ServerLifecycleHooks
import net.minecraftforge.registries.ForgeRegistries
import phoenix.Phoenix.ASH
import phoenix.blocks.redo.ArmoredGlassBlock
import phoenix.init.*
import phoenix.init.PhoenixBlocks.BLOCKS
import phoenix.network.NetworkHandler
import phoenix.utils.block.ICustomGroup
import phoenix.utils.block.INonItem
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.*

object PhoenixCommonEvents
{
    fun onRegisterItems(event: RegistryEvent.Register<Item>)
    {
        if(event.registry == ForgeRegistries.ITEMS)
        {
            val blocks: ArrayList<Block> = ArrayList()

            for (i: ObjectHolderDelegate<out Block> in BLOCKS.getEntries())
            {
                blocks.add(i.get())
            }

            for (block: Block in blocks)
            {
                if (block !is INonItem && block !is FlowingFluidBlock)
                {
                    val tab = if (block is ICustomGroup) block.getTab() else ASH
                    val blockItem = BlockItem(block, Item.Properties().group(tab))
                    blockItem.registryName = block.registryName
                    event.registry.register(blockItem)
                }
            }
        }
    }

    fun init(event: FMLCommonSetupEvent)
    {
        NetworkHandler.init()
        PhoenixRecipes.register()
        PhoenixBiomes.addBiomeTypes()
        GlobalEntityTypeAttributes.put(PhoenixEntities.TALPA, registerTalpaAttributes().create())

        //PotionUtils.appendEffects(ItemStack(Items.POTION), arrayListOf(EffectInstance(Effects.AW, 120, 1)))
        //BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(PhoenixBlocks.SETA), Ingredient.fromItems(Items.))
    }

    private fun registerTalpaAttributes(): AttributeModifierMap.MutableAttribute
    {
        return LivingEntity.registerAttributes()
            .createMutableAttribute(Attributes.MAX_HEALTH, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
            .createMutableAttribute(Attributes.FOLLOW_RANGE)
    }
}