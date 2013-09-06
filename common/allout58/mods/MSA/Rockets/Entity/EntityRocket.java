package allout58.mods.MSA.Rockets.Entity;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import allout58.mods.MSA.Rockets.Rocket;

public class EntityRocket extends Entity
{
    Rocket rocketLogic;
    boolean firstTick = true;

    public EntityRocket(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
    }

    public EntityRocket(World par1World, Rocket rLogic, double x, double y, double z)
    {
        this(par1World);
        this.setPosition(x, y, z);
        rocketLogic = rLogic;
        rocketLogic.Launch();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        rocketLogic = new Rocket();
        rocketLogic.readFromNBT(nbttagcompound);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        rocketLogic.writeToNBT(nbttagcompound);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (firstTick && worldObj.isRemote)
        {
            firstTick=false;
            ticksExisted = 0;
            return;
        }
        if (ticksExisted > 800) this.kill();
        if (rocketLogic == null) return;
        // if (!worldObj.isRemote)
        // {
        rocketLogic.tick();
        this.motionX = rocketLogic.velX;
        this.motionY = rocketLogic.velY;
        this.motionZ = rocketLogic.velZ;
        if (this.motionY < -.5) this.motionY = -.5;//min negative speed is -.5;
        if (this.motionY < 0)
        {
            if (worldObj.getBlockId((int) this.posX, (int) this.posY - 1, (int) this.posZ) != 0)
            {
                // explode
                this.kill();
            }
        }
        // this.motionY=.1D;
        // }
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
        if (worldObj.isRemote)
        {
            Random rand = new Random();
            for (int i = 0; i < (0 + rocketLogic.Size.ordinal()) * (2 - Minecraft.getMinecraft().gameSettings.particleSetting); i++)
            {
                worldObj.spawnParticle("flame", this.posX + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), this.posY - rand.nextDouble(), this.posZ + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1), -rand.nextDouble(), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1));
            }
        }
        if (worldObj.isRemote)
        {
            System.out.println("Rocket entity-Client x:" + posX + " y:" + posY + " z:" + posZ);
        }
        else
        {
            System.out.println("Rocket entity-Server x:" + posX + " y:" + posY + " z:" + posZ);
        }
    }

    @Override
    protected void entityInit()
    {

    }
}
