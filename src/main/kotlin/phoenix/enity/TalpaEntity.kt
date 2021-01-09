package phoenix.enity

import net.minecraft.entity.*
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.monster.AbstractRaiderEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.DamageSource
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import phoenix.init.PhoenixEntities


class TalpaEntity(type: EntityType<TalpaEntity>, worldIn: World) : AnimalEntity(type, worldIn)
{
    val boundOrigin: BlockPos? = null

    override fun tick()
    {
        setNoGravity(isEntityInsideOpaqueBlock)
        super.tick()
    }

    companion object
    {
        init
        {
            val map = AttributeModifierMap.MutableAttribute().createMutableAttribute(Attributes.MAX_HEALTH)
            GlobalEntityTypeAttributes.put(PhoenixEntities.TALPA, map.create())
        }
    }

    override fun isInvulnerableTo(source: DamageSource) = super.isInvulnerableTo(source) || source === DamageSource.IN_WALL
    override fun getMaxFallHeight() = 7
    override fun getSize(poseIn: Pose) = EntitySize(0.6f, 0.6f, false)
    override fun getPoseAABB(pose: Pose): AxisAlignedBB?
    {
        return if (isEntityInsideOpaqueBlock) null else super.getPoseAABB(pose)
    }

    override fun canSpawn(worldIn: IWorld, spawnReasonIn: SpawnReason) = this.position.y in 11..49 && super.canSpawn(worldIn, spawnReasonIn)

    override fun func_241840_a(world: ServerWorld, ageable: AgeableEntity)= TalpaEntity(PhoenixEntities.TALPA, world)
    override fun registerGoals()
    {
        goalSelector.addGoal(0, SwimGoal(this))
        goalSelector.addGoal(1, PanicGoal(this, 0.25))
        goalSelector.addGoal(2, BreedGoal(this, 0.5))
        goalSelector.addGoal(3, TemptGoal(this, 0.5, Ingredient.fromItems(Items.WHEAT), false))
        goalSelector.addGoal(4, FollowParentGoal(this, 0.5))
        goalSelector.addGoal(9, LookAtGoal(this, PlayerEntity::class.java, 3.0f, 1.0f))
        goalSelector.addGoal(10, LookAtGoal(this, MobEntity::class.java, 8.0f))
        targetSelector.addGoal(5, HurtByTargetGoal(this, AbstractRaiderEntity::class.java).setCallsForHelp())
        targetSelector.addGoal(6, NearestAttackableTargetGoal(this, PlayerEntity::class.java, true))
    }


}
