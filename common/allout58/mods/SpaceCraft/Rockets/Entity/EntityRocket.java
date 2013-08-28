package allout58.mods.SpaceCraft.Rockets.Entity;

import java.util.Random;

import allout58.mods.SpaceCraft.Rockets.Rocket;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRocket extends Entity
{
    Rocket rocketLogic;
    
    public EntityRocket(World world) {
        super(world);
    }
    
    public EntityRocket(World par1World, Rocket rLogic, double x, double y, double z)
    {
        super(par1World);
        rocketLogic=rLogic;
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit()
    {
        this.motionX=0;
        this.motionY=0.01;
        this.motionZ=0;
        // TODO Auto-generated method stub

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(ticksExisted>100)
            this.kill();
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
        if(rocketLogic==null)return;
        Random rand = new Random();
        for (int i = 0; i < (rocketLogic.Size.ordinal() + 1) * 4; i++)
        {
            worldObj.spawnParticle("flame", this.posX + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), this.posY - rand.nextDouble(), this.posZ + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), -rand.nextDouble(), rand.nextDouble() * (rand.nextBoolean() ? -1 : 1));
        }
    }

}
