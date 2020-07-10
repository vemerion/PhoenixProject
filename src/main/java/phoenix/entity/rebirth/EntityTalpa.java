package phoenix.entity.rebirth;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import phoenix.entity.AI;

public class EntityTalpa extends EntityLiving
{
    public EntityTalpa(World worldIn)
    {
        super(worldIn);
        setSize(0.6F, 0.6F);
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, new AI.EntityAIItemPickup(this));
    }
}
