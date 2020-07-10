package phoenix.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AI
{
    //Talpa use
    public static class EntityAIItemPickup extends EntityAIBase
    {

        public EntityLiving living;
        public Path path;
        public EntityAIItemPickup.Sorter sorter;
        private EntityItem targetItem;
        public EntityAIItemPickup(EntityLiving living) {
            this.living = living;

            sorter = new EntityAIItemPickup.Sorter(living);

            setMutexBits(1);
        }

        @Override
        public boolean shouldExecute() {

            List<EntityItem> list = living.world.getEntitiesWithinAABB(EntityItem.class, getTargetableArea(10D)); // все предметы в радиусе действия

            if (list.isEmpty()) { // нету предметов?
                return false;
            } else { // есть предметы?
                Collections.sort(list, sorter);
                targetItem = list.get(0); // самый близкий предмет
            }

            if (targetItem == null) {
                return false;
            } else {
                path = living.getNavigator().getPathToEntityLiving(targetItem); // поиск пути до предмета

                return path != null;
            }
        }

        /*
         * Создание зоны, в которой сущность будет брать предметы
         */
        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return living.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
        }

        @Override
        public void resetTask() {
            living.getNavigator().clearPath(); // обнуляем путь
        }

        @Override
        public void updateTask() {
            living.getLookHelper().setLookPositionWithEntity(targetItem, 30F, 30F); // чтобы сущность смотрела на предмет

            double d = living.getDistanceSq(targetItem.posX, targetItem.posY, targetItem.posZ); // дистанция от сущности до предмета

            living.getNavigator().tryMoveToEntityLiving(targetItem, 0.5D); // пробуем добраться до предмета

            if (d <= 1D) { // если предмет очень близко
                living.onItemPickup(targetItem, targetItem.getItem().getCount()); // делаем эффект взятия в инвентарь
                // берём в инвентарь
                targetItem.setDead(); // убираем предмет
            }
        }

        @Override
        public void startExecuting() {
            living.getNavigator().setPath(path, 0.5D); // задаём путь к предмету
        }

        /*
         * Сортировка предметов по дальности от сущности, чтобы взять самый близкий предмет
         */
        class Sorter implements Comparator<EntityItem>
        {
            private final EntityLiving entity;

            public Sorter(EntityLiving entity) {
                this.entity = entity;
            }

            @Override
            public int compare(EntityItem e1, EntityItem e2) {
                double d0 = entity.getDistanceSq(e1);
                double d1 = entity.getDistanceSq(e2);
                if(e1.getItem().getItem() instanceof ItemFood && !(e2.getItem().getItem() instanceof ItemFood))
                {
                    return -1;
                }
                else if(e2.getItem().getItem() instanceof ItemFood && !(e1.getItem().getItem() instanceof ItemFood))
                {
                    return 1;
                }
                if (d0 < d1)
                {
                    return -1;
                }
                else
                {
                    return d0 > d1 ? 1 : 0;
                }
            }
        }
    }
    public static class EntityAIUterusAttack extends EntityAIBase
    {

        public EntityLiving living;
        public Path path;
        public Sorter sorter;
        private EntityLivingBase target;
        public EntityAIUterusAttack(EntityLiving living) {
            this.living = living;
            sorter = new Sorter(living);
            setMutexBits(1);
        }

        @Override
        public boolean shouldExecute() {

            List<EntityLivingBase> list = living.world.getEntitiesWithinAABB(EntityLivingBase.class, getTargetableArea(20D)); // все предметы в радиусе действия

            if (list.isEmpty()) { // нету предметов?
                return false;
            } else { // есть предметы?
                list.sort(sorter);
                target = list.get(0);
            }

            if (target == null) {
                return false;
            } else {
                //path = living.getNavigator().getPathToEntityLiving(target); // поиск пути до предмета
                living.getNavigator().tryMoveToEntityLiving(target, living.getAIMoveSpeed());
                //return path != null;
                return true;
            }
        }

        /*
         * Создание зоны, в которой сущность будет брать предметы
         */
        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return living.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
        }

        @Override
        public void resetTask() {
            living.getNavigator().clearPath(); // обнуляем путь
        }

        @Override
        public void updateTask() {
            living.getLookHelper().setLookPositionWithEntity(target, 30F, 30F); // чтобы сущность смотрела на предмет

            double d = living.getDistanceSq(target.posX, target.posY, target.posZ); // дистанция от сущности до предмета

            living.getNavigator().tryMoveToEntityLiving(target, 0.5D); // пробуем добраться до предмета

            if (d <= 1D) { // если предмет очень близко
                living.attackEntityAsMob(target);
            }
        }

        @Override
        public void startExecuting() {
            living.getNavigator().setPath(path, 0.5D); // задаём путь к предмету
        }

        /*
         * Сортировка предметов по дальности от сущности, чтобы взять самый близкий предмет
         */
        static class Sorter implements Comparator<EntityLivingBase>
        {
            private final EntityLiving entity;

            public Sorter(EntityLiving entity) {
                this.entity = entity;
            }

            @Override
            public int compare(EntityLivingBase e1, EntityLivingBase e2)
            {
                if (e1 instanceof EntityPlayer && !(e2 instanceof EntityPlayer)) {
                    return -1;
                }
                else if (e2 instanceof EntityPlayer && !(e1 instanceof EntityPlayer)) {
                    return 1;
                }
                else {
                    double d0 = entity.getDistanceSq(e1);
                    double d1 = entity.getDistanceSq(e2);
                    if (d0 < d1) {
                        return -1;
                    }
                    else {
                        return d0 > d1 ? 1 : 0;
                    }
                }
            }
        }
    }



}
