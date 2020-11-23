package phoenix.items.ash

import com.google.common.collect.ImmutableSet
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.enchantment.Enchantments.QUICK_CHARGE
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem
import net.minecraft.tags.Tag
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.World
import net.minecraftforge.common.Tags
import phoenix.Phoenix
import phoenix.enity.KnifeEntity
import phoenix.init.events.PhoenixEventsOther.addTask
import phoenix.utils.WorldUtils

class KnifeItem(tier: IItemTier, attackDamageIn: Float, attackSpeedIn: Float, maxUsages: Int) : ToolItem(attackDamageIn, attackSpeedIn, tier, breakableBlocks, Properties().group(Phoenix.PHOENIX).maxDamage(maxUsages))
{
    val damage: Float = attackDamageIn + tier.attackDamage

    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack>
    {
        val itemstack = player.getHeldItem(hand)
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f))
        var coolDown = 10
        coolDown -= (1.5 * EnchantmentHelper.getEnchantmentLevel(QUICK_CHARGE, itemstack)).toInt()
        player.cooldownTracker.setCooldown(this, coolDown)
        if (!world.isRemote)
        {
            val knife = KnifeEntity(world, player, EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) == 0)
            knife.knife = itemstack
            knife.shoot(player, player.rotationPitch, player.rotationYaw, 0.0f, 2f, 0.3f)
            world.addEntity(knife)
            val count = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, itemstack)
            for (i in 1..count)
            {
                addTask(10 * i)
                {
                    val knife2 = KnifeEntity(world, player, false)
                    knife2.shoot(player, player.rotationPitch, player.rotationYaw, 0.0f, 2.5f, 1.0f)
                    world.addEntity(knife2)
                }
            }
            if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) == 0)
                player.setHeldItem(hand, ItemStack.EMPTY)
        }

        return ActionResult(ActionResultType.SUCCESS, itemstack)
    }

    fun onHitBlock(world: World, owner: LivingEntity, pos: BlockPos, knife: KnifeEntity, item: ItemStack): Boolean
    {
        var shouldBroke = false
        val block = world.getBlockState(pos).block
        for (tag in breakableBlocksTypes) if (block.isIn(tag))
        {
            shouldBroke = true
            break
        }
        if(block == Blocks.TNT && EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, item) > 0)
        {
            WorldUtils.destroyBlock(world, pos, false, owner, item)
            world.createExplosion(knife, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 4F, true, Explosion.Mode.BREAK)

            return false
        }
        else if (breakableBlocks.contains(world.getBlockState(pos).block) || shouldBroke)
        {
            WorldUtils.destroyBlock(world, pos, true, owner, item)
            knife.knife.attemptDamageItem(1, world.rand, null)
        }
        return !(block !== Blocks.GRASS_BLOCK && block !== Blocks.SNOW && block.isIn(Tags.Blocks.SAND))
    }

    fun onHitEntity(world: World, owner: LivingEntity, knife: KnifeEntity, hitted: Entity, knifeItem: ItemStack): Boolean
    {
        val powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, knifeItem)
        val damage = damage + powerLevel.toDouble() * 0.6
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, knifeItem) > 0 && hitted.type !== EntityType.SNOW_GOLEM) hitted.setFire(100)
        hitted.attackEntityFrom(DamageSource.causeThrownDamage(knife, knife.thrower), damage.toFloat())

        return hitted.type !== EntityType.SNOW_GOLEM && hitted.type !== EntityType.END_CRYSTAL && hitted.type !== EntityType.PANDA
    }

    override fun isEnchantable(stack: ItemStack) = true

    override fun canApplyAtEnchantingTable(stack: ItemStack, enchantment: Enchantment) = allowedEnchantments.contains(enchantment)

    companion object
    {
        var breakableBlocks: Set<Block> = ImmutableSet.of(Blocks.SPONGE, Blocks.VINE, Blocks.SEA_PICKLE, Blocks.WET_SPONGE, Blocks.GRASS, Blocks.TALL_GRASS, Blocks.SUGAR_CANE)
        var breakableBlocksTypes: Set<Tag<Block>> = ImmutableSet.of(Tags.Blocks.GLASS, Tags.Blocks.STAINED_GLASS_PANES)
        var allowedEnchantments: Set<Enchantment> = ImmutableSet.of(Enchantments.POWER, QUICK_CHARGE, Enchantments.MENDING, Enchantments.FLAME, Enchantments.SILK_TOUCH, Enchantments.UNBREAKING)
    }
}